package com.shridarshan.in.services;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.shridarshan.in.data.IDataLoader;
import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.util.BeanConstants;

public class TempleService implements ApplicationContextAware, ITempleService {

	private AbstractApplicationContext context;
	private IDataLoader dataLoader;
	
	@Override
	public List<ITemple> getTempleList() {
		this.dataLoader = (IDataLoader) context.getBean(BeanConstants.DATA_LOADER);
		return dataLoader.getTempleList();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;

	}
}
