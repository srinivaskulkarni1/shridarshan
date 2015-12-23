package com.shridarshan.in.automation_test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

public class DataCacheQuery {

	private String entityType;

	@SuppressWarnings("rawtypes")
	private static Map<Class, String> classesMap = new HashMap<Class, String>();

	public DataCacheQuery(String entityType) {
		this.entityType = entityType;
	}

	@SuppressWarnings("unchecked")
	public List<Object> query() {
		List<Object> objList = new ArrayList<Object>();

		if (DataCache.getCache().get(entityType) != null) {
			objList = (List<Object>) DataCache.getCache().get(entityType);

			return convertCollection(objList);
		}
		return null;
	}

	@SuppressWarnings({ })
	public static List<Object> convertCollection(List<Object> list) {
		List<Object> result = new ArrayList<Object>();
		for (Object element : list) {
			List<Object> row = new ArrayList<Object>();
			getValues(row, null, element);
			result.add(row);
		}
		printResult(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	private static void printResult(List<Object> result) {
		for (Object rows : result) {
			List<Object> rowList = (List<Object>) rows;

			System.out.println("---------- Field Values -----------");

			for (Object rowObject : rowList) {
				List<Object> row = (List<Object>) rowObject;
				System.out.println(row.get(0) + " " + row.get(1));
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void getValues(List<Object> row, String prefix, Object o) {
		if (o != null) {
			try {
				BeanInfo info = Introspector.getBeanInfo(o.getClass());
				for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
					Method getter = pd.getReadMethod();
					if (getter != null) {
						if (classesMap.containsKey(getter.getReturnType())) {
							getValues(row,
									classesMap.get(getter.getReturnType()),
									getter.invoke(o));
						} else if (getter.getReturnType().equals(List.class)) {
							List<Object> collection = (List<Object>) getter
									.invoke(o);
							if (collection != null) {
								List<Object> internal = new ArrayList<Object>();
								for (Object real : collection) {
									List<Object> collMapp = new ArrayList<Object>();
									System.out.println(real);
									getValues(collMapp, null, real);
									StringBuilder builder = new StringBuilder();
									for (Object list : collMapp) {
										List<Object> rows = (List<Object>) list;
										builder.append("[");
										if (rows != null && rows.size() > 0) {
											builder.append(rows.get(0))
													.append(":")
													.append(rows.get(1));
										}
										builder.append("]");
									}
									internal.add(builder.toString());
								}
								row.add(Utility.list((prefix == null ? ""
										: prefix + ".") + pd.getName(),
										internal.toString()));
							} else {
								row.add(Utility.list((prefix == null ? ""
										: prefix + ".") + pd.getName(),
										getter.invoke(o)));
							}
						} else if (getter.getReturnType().equals(
								XMLGregorianCalendar.class)) {

						} else if (!(pd.getName().equalsIgnoreCase("Class"))) {
							row.add(Utility.list((prefix == null ? "" : prefix
									+ ".")
									+ pd.getName(), getter.invoke(o)));
						}
					}
				}
			} catch (IllegalAccessException e) {
				System.out.println("Error in DataCacheQuery.getValues: "
						+ "IllegalAccessException");
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				System.out.println("Error in DataCacheQuery.getValues: "
						+ "IllegalArgumentException");
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				System.out.println("Error in DataCacheQuery.getValues: "
						+ "InvocationTargetException");
				e.printStackTrace();
			} catch (IntrospectionException e) {
				System.out.println("Error in DataCacheQuery.getValues: "
						+ "IntrospectionException");
				e.printStackTrace();
			}
		}
	}
	

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

}
