package com.spring.linuxmate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.linuxmate.dto.DiskDTO;
import com.spring.linuxmate.dto.ProcessDTO;
import com.spring.linuxmate.dto.ServiceDTO;
import com.spring.linuxmate.dto.SystemStatsDTO;

@Service
public class SystemService {
	
	@Autowired
	SshService sshService;
	
	
	public List<String> getUsers() {

	    String output = sshService.executeCommand("cut -d: -f1 /etc/passwd");

	    return Arrays.stream(output.split("\n"))
	            .map(String::trim)
	            .filter(line -> !line.isEmpty())
	            .collect(Collectors.toList());
	}
	
	public Map<String, String> getAlerts() {

	    // reuse your stats logic OR call commands again
	    String diskOutput = sshService.executeCommand("df -h /");
	    String memoryOutput = sshService.executeCommand("free -m");

	    // ---- DISK ----
	    String diskLine = diskOutput.split("\n")[1];
	    String usageStr = diskLine.trim().replaceAll("\\s+", " ").split(" ")[4];
	    int diskUsage = Integer.parseInt(usageStr.replace("%", ""));

	    String diskStatus = diskUsage > 90 ? "CRITICAL" :
	                        diskUsage > 80 ? "WARNING" : "NORMAL";

	    // ---- MEMORY ----
	    String memLine = memoryOutput.split("\n")[1];
	    String[] parts = memLine.trim().replaceAll("\\s+", " ").split(" ");

	    int total = Integer.parseInt(parts[1]);
	    int used = Integer.parseInt(parts[2]);

	    int memPercent = (used * 100) / total;

	    String memStatus = memPercent > 90 ? "CRITICAL" :
	                       memPercent > 75 ? "WARNING" : "NORMAL";

	    return Map.of(
	        "disk", diskStatus,
	        "memory", memStatus
	    );
	}
	
	
	public List<String> getLogs() {

	    String output = sshService.executeCommand("journalctl -n 50 --no-pager");

	    return Arrays.stream(output.split("\n"))
	            .map(String::trim)
	            .collect(Collectors.toList());
	}
	
	
	public List<ServiceDTO> getServices() {

	    String output = sshService.executeCommand(
	        "systemctl list-units --type=service --no-pager --no-legend"
	    );

	    String[] lines = output.split("\n");
	    List<ServiceDTO> list = new ArrayList<>();

	    for (String line : lines) {

	        line = line.trim().replaceAll("\\s+", " ");
	        String[] parts = line.split(" ");

	        if (parts.length < 5) continue;

	        String name = parts[0];
	        String load = parts[1];
	        String active = parts[2];
	        String sub = parts[3];

	        String description = String.join(" ",
	                Arrays.copyOfRange(parts, 4, parts.length));

	        list.add(new ServiceDTO(name, load, active, sub, description));
	    }

	    return list;
	}
	
	public SystemStatsDTO getStats() {

	    // CPU
	    String cpuOutput = sshService.executeCommand("top -bn1 | grep 'Cpu(s)'");
	    double cpuUsage = parseCpu(cpuOutput);

	    // Memory
	    String memOutput = sshService.executeCommand("free -m");
		long total = 0;
		long used = 0;
		long free = 0;
		try {
			String memLine = Arrays.stream(memOutput.split("\n"))
					.map(String::trim)
					.filter(l -> l.startsWith("Mem:"))
					.findFirst()
					.orElse("");

			if (!memLine.isEmpty()) {
				String[] mparts = memLine.replaceAll("\\s+", " ").split(" ");
				// expected: Mem: total used free shared buff/cache available
				if (mparts.length >= 4) {
					total = Long.parseLong(mparts[1]);
					used = Long.parseLong(mparts[2]);
					free = Long.parseLong(mparts[3]);
				}
			}
		} catch (Exception e) {
			// parsing failed - leave defaults (0)
		}

	    // Disk
	    String diskOutput = sshService.executeCommand("df -h /");
		String totalDisk = "";
		String usedDisk = "";
		String availDisk = "";
		int usage = 0;
		try {
			String[] dlines = diskOutput.split("\n");
			if (dlines.length > 1) {
				String dline = dlines[1].trim().replaceAll("\\s+", " ");
				String[] dparts = dline.split(" ");
				if (dparts.length >= 5) {
					totalDisk = dparts[1];
					usedDisk = dparts[2];
					availDisk = dparts[3];
					usage = Integer.parseInt(dparts[4].replace("%", ""));
				}
			}
		} catch (Exception e) {
			// fallback to defaults
		}

	    return new SystemStatsDTO(
	        cpuUsage,
	        total, used, free,
			totalDisk, usedDisk, availDisk, usage
	    );
	}
    
	// Parse a line from `top` like:
	// "%Cpu(s):  1.2 us,  0.3 sy,  0.0 ni, 98.1 id,  0.3 wa,  0.0 hi,  0.1 si,  0.0 st"
	// We compute cpu usage as 100 - idle
	private double parseCpu(String cpuOutput) {
		if (cpuOutput == null) return 0.0;
		try {
			Pattern p = Pattern.compile("([0-9]+\\.[0-9]+|[0-9]+)\\s*id");
			Matcher m = p.matcher(cpuOutput);
			if (m.find()) {
				double idle = Double.parseDouble(m.group(1));
				return Math.round((100.0 - idle) * 100.0) / 100.0; // round to 2 decimals
			}

			// fallback: try extracting us and sy
			Pattern p2 = Pattern.compile("([0-9]+\\.[0-9]+|[0-9]+)\\s*us,\\s*([0-9]+\\.[0-9]+|[0-9]+)\\s*sy");
			Matcher m2 = p2.matcher(cpuOutput);
			if (m2.find()) {
				double us = Double.parseDouble(m2.group(1));
				double sy = Double.parseDouble(m2.group(2));
				return Math.round((us + sy) * 100.0) / 100.0;
			}
		} catch (Exception e) {
			// ignore and fallback to 0
		}
		return 0.0;
	}
	public List<DiskDTO> getDiskPartitions() {

	    String output = sshService.executeCommand("df -h");

	    String[] lines = output.split("\n");
	    List<DiskDTO> list = new ArrayList<>();

	    for (int i = 1; i < lines.length; i++) { // skip header

	        String line = lines[i].trim().replaceAll("\\s+", " ");
	        String[] parts = line.split(" ");

	        if (parts.length < 6) continue;

	        DiskDTO d = new DiskDTO(
	                parts[0], // filesystem
	                parts[1], // size
	                parts[2], // used
	                parts[3], // available
	                parts[4], // usage
	                parts[5]  // mount
	        );

	        list.add(d);
	    }

	    return list;
	}
	
	public List<ProcessDTO> getProcesses() {

	    String output = sshService.executeCommand(
	        "ps -eo pid,user,%cpu,%mem,comm --sort=-%cpu"
	    );

	    String[] lines = output.split("\n");

	    List<ProcessDTO> list = new ArrayList<>();

	    for (int i = 1; i < lines.length; i++) { 

	        String line = lines[i].trim().replaceAll("\\s+", " ");
	        String[] parts = line.split(" ");

	        if (parts.length < 5) continue;

	        ProcessDTO p = new ProcessDTO(
	            Integer.parseInt(parts[0]),
	            parts[1],
	            Double.parseDouble(parts[2]),
	            Double.parseDouble(parts[3]),
	            parts[4]
	        );

	        list.add(p);
	    }

	    return list;
	}

}
