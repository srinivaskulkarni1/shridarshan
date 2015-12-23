package com.shridarshan.in.automation_test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utility {
	
	@SuppressWarnings("rawtypes")
	public static List<Object> getCollection(Collection collection){
		List<Object> objList = new ArrayList<Object>();
		for (Object object : collection) {
			objList.add(getValues(object));
		}
		
		return objList;
	}

	private static Object getValues(Object object) {
		List<Object> objList = new ArrayList<Object>();
		try {
			BeanInfo info = Introspector.getBeanInfo(object.getClass());
			for(PropertyDescriptor d : info.getPropertyDescriptors()){
				Method get = d.getReadMethod();
				if(get != null){
					objList.add(list(d.getName(), get.invoke(object)));
				}
			}
		} catch (IntrospectionException e) {
			System.out.println("Exception in method Utility.getValues(): " + "IntrospectionException");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("Exception in method Utility.getValues(): " + "IllegalAccessException");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println("Exception in method Utility.getValues(): " + "IllegalArgumentException");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.out.println("Exception in method Utility.getValues(): " + "InvocationTargetException");
			e.printStackTrace();
		}
		return objList;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> list(T... objects){
		List<T> list = new ArrayList<T>();
		for(T obj : objects){
			list.add(obj);
		}
		return list;
	}
}
