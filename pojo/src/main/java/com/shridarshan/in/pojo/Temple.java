package com.shridarshan.in.pojo;

import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.Objects;
import com.shridarshan.in.util.DBConstants;

@Table(keyspace=DBConstants.KEYSPACE, name=DBConstants.TABLE_TEMPLE)
public class Temple implements ITemple {
	
	@PartitionKey(0)
	private String god;
	@PartitionKey(1)
	private String place;
	
	private String state;
	private String district;

	public Temple() {
	}
	
	public Temple(String god, String place, String state, String district) {
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
	
	@Override
	public boolean equals(Object obj) {
        if (obj instanceof Temple) {
        	Temple that = (Temple) obj;
            return Objects.equal(this.god, that.god) &&
                   Objects.equal(this.place, that.place);
        }
        return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(god, place);
	}
}
