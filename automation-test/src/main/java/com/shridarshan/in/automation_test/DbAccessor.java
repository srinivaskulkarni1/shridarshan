package com.shridarshan.in.automation_test;

import java.util.ArrayList;
import java.util.List;

import com.datastax.driver.core.Session;

public class DbAccessor {

	private String action;
	private String table;
	private List<String> values = new ArrayList<>();

	public void setAction(String action) {
		this.action = action;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public boolean status() {
		if (action.equalsIgnoreCase("insert")) {
			return true;
		} else if (action.equalsIgnoreCase("clear")) {
			return true;
		}

		return true;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	/*
	 * public DbAccessor(String query){
	 * 
	 * @SuppressWarnings("unused") Session session = DbConnection.getSession();
	 * 
	 * } public List<Object> query(){
	 * 
	 * @SuppressWarnings("unused") Session session = DbConnection.getSession();
	 * return null; }
	 */
}
