package com.shridarshan.in.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;

import com.shridarshan.in.model.Temple;

public class TempleService {

	public List<Temple> getTempleList(AbstractApplicationContext context){
		Temple temple = (Temple) context.getBean("temple");
		temple.setGod("Shri Krishna");
		temple.setDistrict("Udupi");
		temple.setPlace("Udupi");
		temple.setState("Karnataka");

		List<Temple> templeList = new ArrayList<Temple>();
		templeList.add(temple);
		return templeList;
	}
}
