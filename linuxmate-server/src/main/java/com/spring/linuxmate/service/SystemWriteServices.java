package com.spring.linuxmate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemWriteServices {
	
	@Autowired
	SshService sshService;
	
	public String killProcess(int pid, String user) {

	    if (pid <= 0) {
	        throw new RuntimeException("Invalid PID");
	    }

	    String result = sshService.executeCommand("kill -9 " + pid);
	    
	    System.out.println(user+" "+"KILL_PROCESS" +" "+ String.valueOf(pid));

//	    auditService.log(user, "KILL_PROCESS", String.valueOf(pid));

	    return "Process " + pid + " killed successfully";
	}
	
	public String restartService(String name, String user) {

	    if (name == null || name.isEmpty()) {
	        throw new RuntimeException("Invalid service name");
	    }

	    if (name.contains(" ")) {
	        throw new RuntimeException("Invalid service name");
	    }

	    sshService.executeCommand("sudo systemctl restart " + name);

//	    auditService.log(user, "RESTART_SERVICE", name);

	    return "Service " + name + " restarted";
	}
	public String startService(String name, String user) {

	    if (name == null || name.isEmpty()) {
	        throw new RuntimeException("Invalid service name");
	    }

	    if (name.contains(" ")) {
	        throw new RuntimeException("Invalid service name");
	    }

	    sshService.executeCommand("sudo systemctl start " + name);

//	    auditService.log(user, "RESTART_SERVICE", name);

	    return "Service " + name + " started";
	}
	public String stopService(String name, String user) {

	    if (name == null || name.isEmpty()) {
	        throw new RuntimeException("Invalid service name");
	    }

	    if (name.contains(" ")) {
	        throw new RuntimeException("Invalid service name");
	    }
	    System.out.println(name+" "+"STOP_SERVICE");
	    String executeCommand = sshService.executeCommand("uptime");
	    
	    System.out.println(executeCommand);

	    sshService.executeCommand("sudo systemctl stop " + name);
	    

//	    auditService.log(user, "RESTART_SERVICE", name);

	    return "Service " + name + " stopeed";
	}

}
