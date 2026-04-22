package com.spring.linuxmate.dto;

public class UptimeDTO {
    private String uptime;
    private int users;
    private double load1;
    private double load5;
    private double load15;
    
	
	public UptimeDTO(String uptime, int users, double load1, double load5, double load15) {
		super();
		this.uptime = uptime;
		this.users = users;
		this.load1 = load1;
		this.load5 = load5;
		this.load15 = load15;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public int getUsers() {
		return users;
	}
	public void setUsers(int users) {
		this.users = users;
	}
	public double getLoad1() {
		return load1;
	}
	public void setLoad1(double load1) {
		this.load1 = load1;
	}
	public double getLoad5() {
		return load5;
	}
	public void setLoad5(double load5) {
		this.load5 = load5;
	}
	public double getLoad15() {
		return load15;
	}
	public void setLoad15(double load15) {
		this.load15 = load15;
	}
}