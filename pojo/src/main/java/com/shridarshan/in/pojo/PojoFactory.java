package com.shridarshan.in.pojo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.shridarshan.in.util.BeanConstants;

public class PojoFactory implements ApplicationContextAware {

	private AbstractApplicationContext context;

	public Temple getTemplePojo() {
		return (Temple) context.getBean(BeanConstants.TEMPLE);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;
	}
}
