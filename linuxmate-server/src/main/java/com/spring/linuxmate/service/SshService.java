package com.spring.linuxmate.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.spring.linuxmate.dto.UptimeDTO;

@Service
public class SshService {
	
	
	
	
	public UptimeDTO getUptime() {

	    String output = executeCommand("uptime");

	    // Example:
	    // "11:41:44 up 38 min,  6 users,  load average: 0.07, 0.06, 0.07"

	    String afterUp = output.split("up")[1].trim();

	    String uptime = afterUp.split(",")[0].trim();

	    int users = Integer.parseInt(
	            afterUp.split(",")[1].trim().split(" ")[0]
	    );

	    String loadPart = output.split("load average:")[1].trim();

	    String[] loads = loadPart.split(",");

	    double load1 = Double.parseDouble(loads[0].trim());
	    double load5 = Double.parseDouble(loads[1].trim());
	    double load15 = Double.parseDouble(loads[2].trim());

	    return new UptimeDTO(uptime, users, load1, load5, load15);
	}
	

	    public String executeCommand(String command) {
	        String host = "192.168.0.110"; 
	        String user = "linuxmate";
	        String password = "abc12def";

	        StringBuilder output = new StringBuilder();

	        try {
	            JSch jsch = new JSch();
	            Session session = jsch.getSession(user, host, 22);
	            session.setPassword(password);

	            session.setConfig("StrictHostKeyChecking", "no");
	            session.connect();

	            ChannelExec channel = (ChannelExec) session.openChannel("exec");
	            channel.setCommand(command);

	            InputStream in = channel.getInputStream();
	            channel.connect();

	            byte[] tmp = new byte[1024];
	            while (true) {
	                while (in.available() > 0) {
	                    int i = in.read(tmp, 0, 1024);
	                    if (i < 0) break;
	                    output.append(new String(tmp, 0, i));
	                }
	                if (channel.isClosed()) break;
	            }

	            channel.disconnect();
	            session.disconnect();

	        } catch (Exception e) {
	            return "ERROR: " + e.getMessage();
	        }

	        return output.toString();
	    }
	}


