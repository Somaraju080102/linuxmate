package com.spring.linuxmate.dto;

public class ProcessDTO {
	
	private int pid;
    private String user;
    private double cpu;
    private double memory;
    private String command;
	public ProcessDTO(int pid, String user, double cpu, double memory, String command) {
		super();
		this.pid = pid;
		this.user = user;
		this.cpu = cpu;
		this.memory = memory;
		this.command = command;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public double getCpu() {
		return cpu;
	}
	public void setCpu(double cpu) {
		this.cpu = cpu;
	}
	public double getMemory() {
		return memory;
	}
	public void setMemory(double memory) {
		this.memory = memory;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
    
    
    

}
