package com.spring.linuxmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.linuxmate.dto.UptimeDTO;
import com.spring.linuxmate.service.SshService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	SshService sshService;
	
	@GetMapping("/hi")
	public String getMessage() {
		
		return "Hello Somaraju";
	}
	@GetMapping("/system/uptime")
	public UptimeDTO getUptime() {
	    return sshService.getUptime();
	}
	@GetMapping("/ping")
	public String getOutput() {
		return sshService.executeCommand("uptime");
	}
}
