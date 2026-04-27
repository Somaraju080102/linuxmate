package com.spring.linuxmate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.linuxmate.service.SystemWriteServices;

@RestController
public class SystemWriteController {
	
	@Autowired
	SystemWriteServices systemService;
	
	@DeleteMapping("/processes/{pid}")
	public ResponseEntity<?> killProcess(@PathVariable int pid,
	                                     Authentication auth) {

	    String user = auth.getName();

	    String response = systemService.killProcess(pid, user);

	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/services/{name}/restart")
	public ResponseEntity<?> restartService(@PathVariable String name,
	                                        Authentication auth) {

	    String user = auth.getName();

	    String response = systemService.restartService(name, user);

	    return ResponseEntity.ok(response);
	}
	@PostMapping("/services/{name}/start")
	public ResponseEntity<?> startService(@PathVariable String name,
	                                        Authentication auth) {

	    String user = auth.getName();

	    String response = systemService.startService(name, user);

	    return ResponseEntity.ok(response);
	}
	@PostMapping("/services/{name}/stop")
	public ResponseEntity<?> stopService(@PathVariable String name,
	                                        Authentication auth) {

	    String user = auth.getName();

	    String response = systemService.stopService(name, user);

	    return ResponseEntity.ok(response);
	}
	

}
