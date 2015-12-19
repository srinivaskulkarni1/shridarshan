package com.shridarshan.in.services;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.shridarshan.in.data.DataLoader;
import com.shridarshan.in.pojo.Temple;
import com.shridarshan.in.util.BeanConstants;

public class TempleService implements ApplicationContextAware {

	private AbstractApplicationContext context;

	public List<Temple> getTempleList() {

		DataLoader dataLoader = (DataLoader) context.getBean(BeanConstants.DATA_LOADER);

		return dataLoader.getTempleList();

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;
	}
}
