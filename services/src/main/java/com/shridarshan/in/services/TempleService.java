package com.shridarshan.in.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static Logger LOGGER = LoggerFactory.getLogger(TempleService.class);

	
	@Override
	public List<ITemple> getTempleList() {
		LOGGER.debug("Processing {}.getTempleList", TempleService.class.getSimpleName());

		this.dataLoader = (IDataLoader) context.getBean(BeanConstants.DATA_LOADER);
		List<ITemple> templeList = dataLoader.getTempleList();
		LOGGER.debug("Processed {}.getTempleList", TempleService.class.getSimpleName());

		return templeList;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;

	}
}
