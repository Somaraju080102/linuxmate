package com.spring.linuxmate.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.linuxmate.dto.DiskDTO;
import com.spring.linuxmate.dto.ProcessDTO;
import com.spring.linuxmate.dto.ServiceDTO;

@Service
public class SystemService {
	
	@Autowired
	SshService sshService;
	
	
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
	
	public Map<String, String> getStats() {

	    String cpu = sshService.executeCommand("top -bn1 | grep 'Cpu(s)'");
	    String memory = sshService.executeCommand("free -m");
	    String disk = sshService.executeCommand("df -h /");

	    return Map.of(
	        "cpu", cpu.trim(),
	        "memory", memory.trim(),
	        "disk", disk.trim()
	    );
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
