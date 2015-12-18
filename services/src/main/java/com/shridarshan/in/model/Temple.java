package com.shridarshan.in.model;

public class Temple {
	
	private String god;
	private String place;
	private String state;
	private String district;

	public Temple() {
	}
	
	public Temple(String god, String place, String state, String district) {
		super();
		this.god = god;
		this.place = place;
		this.state = state;
		this.district = district;
	}
	
	public String getGod() {
		return god;
	}
	public void setGod(String god) {
		this.god = god;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
}
