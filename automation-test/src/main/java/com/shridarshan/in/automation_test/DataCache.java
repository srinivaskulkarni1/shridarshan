package com.shridarshan.in.automation_test;

import java.util.HashMap;

public class DataCache {

	private HashMap<String, Object> dataChahe = new HashMap<String, Object>();

	private static DataCache cache = null;

	static {
		cache = new DataCache();
	}

	public DataCache() {
	}

	public void add(String query, Object obj) {
		dataChahe.put(query, obj);
	}

	public void remove(String query) {
		dataChahe.remove(query);
	}

	public Object get(String query) {
		return dataChahe.get(query);
	}

	public void clear() {
		dataChahe.clear();
	}

	public static DataCache getCache() {
		return cache;
	}
}
