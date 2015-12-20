package com.shridarshan.in.pojo;

public class Temple implements ITemple {
	
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
	
	@Override
	public String getGod() {
		return god;
	}

	@Override
	public void setGod(String god) {
		this.god = god;
	}

	@Override
	public String getPlace() {
		return place;
	}

	@Override
	public void setPlace(String place) {
		this.place = place;
	}

	@Override
	public String getState() {
		return state;
	}

	@Override
	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String getDistrict() {
		return district;
	}

	@Override
	public void setDistrict(String district) {
		this.district = district;
	}
}
