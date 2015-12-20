package com.shridarshan.in.testdata;

import java.util.ArrayList;
import java.util.List;

import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.pojo.Temple;

public class TestData {

	public static List<ITemple> getDummyTempleList(){
		List<ITemple> templeList = new ArrayList<ITemple>();
		ITemple t = new Temple();
		t.setDistrict("Udupi");
		t.setGod("Shri Krishna");
		t.setPlace("Udupi");
		t.setState("Karnataka");
		templeList.add(t);
		return templeList;
	}
}
