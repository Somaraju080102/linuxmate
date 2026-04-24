package com.spring.linuxmate.dto;

public class SystemStatsDTO {

    private double cpuUsage;

    private long totalMemory;
    private long usedMemory;
    private long freeMemory;

    private String totalDisk;
    private String usedDisk;
    private String availableDisk;
    private int diskUsagePercent;
	public double getCpuUsage() {
		return cpuUsage;
	}
	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public long getTotalMemory() {
		return totalMemory;
	}
	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}
	public long getUsedMemory() {
		return usedMemory;
	}
	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}
	public long getFreeMemory() {
		return freeMemory;
	}
	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}
	public String getTotalDisk() {
		return totalDisk;
	}
	public void setTotalDisk(String totalDisk) {
		this.totalDisk = totalDisk;
	}
	public String getUsedDisk() {
		return usedDisk;
	}
	public void setUsedDisk(String usedDisk) {
		this.usedDisk = usedDisk;
	}
	public String getAvailableDisk() {
		return availableDisk;
	}
	public void setAvailableDisk(String availableDisk) {
		this.availableDisk = availableDisk;
	}
	public int getDiskUsagePercent() {
		return diskUsagePercent;
	}
	public void setDiskUsagePercent(int diskUsagePercent) {
		this.diskUsagePercent = diskUsagePercent;
	}
	public SystemStatsDTO(double cpuUsage, long totalMemory, long usedMemory, long freeMemory, String totalDisk,
			String usedDisk, String availableDisk, int diskUsagePercent) {
		super();
		this.cpuUsage = cpuUsage;
		this.totalMemory = totalMemory;
		this.usedMemory = usedMemory;
		this.freeMemory = freeMemory;
		this.totalDisk = totalDisk;
		this.usedDisk = usedDisk;
		this.availableDisk = availableDisk;
		this.diskUsagePercent = diskUsagePercent;
	}
}
