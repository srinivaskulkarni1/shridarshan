package com.shridarshan.in.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.shridarshan.in.pojo.PojoFactory;
import com.shridarshan.in.pojo.Temple;
import com.shridarshan.in.util.BeanConstants;

public class DataLoader implements ApplicationContextAware{

	private AbstractApplicationContext context;

	public List<Temple> getTempleList() {
		
		PojoFactory pojoFactory = (PojoFactory) context.getBean(BeanConstants.POJO_FACTORY);
		Temple temple = pojoFactory.getTemplePojo();
		temple.setGod("Shri Krishna");
		temple.setDistrict("Udupi");
		temple.setPlace("Udupi");
		temple.setState("Karnataka");

		List<Temple> templeList = new ArrayList<Temple>();
		templeList.add(temple);
		return templeList;

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;
	}
}
