package com.spring.linuxmate.dto;

public class ServiceDTO {
    private String name;
    private String load;
    private String active;
    private String sub;
    private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoad() {
		return load;
	}
	public void setLoad(String load) {
		this.load = load;
	}
	public ServiceDTO(String name, String load, String active, String sub, String description) {
		super();
		this.name = name;
		this.load = load;
		this.active = active;
		this.sub = sub;
		this.description = description;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}