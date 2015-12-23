package com.shridarshan.in.automation_test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shridarshan.in.pojo.Temple;

public class Main {

	public static void main(String[] args) {
		String jSonString = "[{'district': ' Ahmednagar', 'god': 'Shirdi Sai Baba', 'place': ' Shirdi', 'state': ' Maharashtra' }, { 'district': ' Udupi', 'god': 'Shri Krishna', 'place': ' Udupi', 'state': ' Karnataka' } ]";
		Gson gson = new Gson();
		Temple[] temples = gson.fromJson(jSonString, Temple[].class);
		
		for (int i = 0; i < temples.length; i++) {
			System.out.println(temples[i].getGod());
		}
	}

}
