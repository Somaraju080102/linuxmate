package com.spring.linuxmate.dto;

public class DiskDTO {
    private String filesystem;
    private String size;
    private String used;
    private String available;
    private String usage;
    private String mount;
	public String getFilesystem() {
		return filesystem;
	}
	public void setFilesystem(String filesystem) {
		this.filesystem = filesystem;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getAvailable() {
		return available;
	}
	public void setAvailable(String available) {
		this.available = available;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getMount() {
		return mount;
	}
	public void setMount(String mount) {
		this.mount = mount;
	}
	public DiskDTO(String filesystem, String size, String used, String available, String usage, String mount) {
		super();
		this.filesystem = filesystem;
		this.size = size;
		this.used = used;
		this.available = available;
		this.usage = usage;
		this.mount = mount;
	}
}