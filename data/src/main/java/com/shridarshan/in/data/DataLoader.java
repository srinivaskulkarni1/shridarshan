package com.shridarshan.in.data;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.Result;
import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.pojo.Temple;
import com.shridarshan.in.util.BeanConstants;
import com.shridarshan.in.util.DBConstants;

public class DataLoader implements ApplicationContextAware, IDataLoader {

	private AbstractApplicationContext context;
	private IDBConnection dbConnection;
	private static Logger LOGGER = LoggerFactory.getLogger(DataLoader.class);


	@Override
	public List<ITemple> getTempleList() {
		LOGGER.debug("Processing {}.getTempleList", DataLoader.class.getSimpleName());

		dbConnection = getDBConnection();

		List<ITemple> templeList = new ArrayList<ITemple>();
		ResultSet resultSet = dbConnection
				.getResultSet(DBConstants.TABLE_TEMPLE);

		if (resultSet != null) {
			Mapper<Temple> mapper = dbConnection.getManager().mapper(
					Temple.class);
			Result<Temple> results = mapper.map(resultSet);

			if (results != null) {
				for (Temple temple : results) {
					templeList.add(temple);
				}
			}
		}
		LOGGER.debug("Processed {}.getTempleList", DataLoader.class.getSimpleName());

		return templeList;
	}

	private IDBConnection getDBConnection() {
		return (IDBConnection) context
				.getBean(BeanConstants.DB_CONNECTION);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;
	}
}
