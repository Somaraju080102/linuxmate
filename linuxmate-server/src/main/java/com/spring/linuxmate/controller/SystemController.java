package com.spring.linuxmate.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.linuxmate.dto.DiskDTO;
import com.spring.linuxmate.dto.ProcessDTO;
import com.spring.linuxmate.dto.ServiceDTO;
import com.spring.linuxmate.service.SystemService;

@RestController
@RequestMapping("/system")
public class SystemController {
	
	
	@Autowired
	SystemService systemService;
	
	@GetMapping("/stats")
	public Map<String, String> getStats() {
	    return systemService.getStats();
	}
	
	@GetMapping("/processes")
	public List<ProcessDTO> getProcesses() {
	    return systemService.getProcesses();
	}
	
	@GetMapping("/services")
	public List<ServiceDTO> getServices() {
	    return systemService.getServices();
	}
	
	@GetMapping("/disks/partitions")
	public List<DiskDTO> getDisks() {
	    return systemService.getDiskPartitions();
	}
	
	@GetMapping("/logs")
	public List<String> getLogs() {
	    return systemService.getLogs();
	}

}
