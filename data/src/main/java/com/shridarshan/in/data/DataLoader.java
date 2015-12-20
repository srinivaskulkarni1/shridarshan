package com.shridarshan.in.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.pojo.PojoFactory;
import com.shridarshan.in.util.BeanConstants;
import com.shridarshan.in.util.DBConstants;

public class DataLoader implements ApplicationContextAware, IDataLoader {

	private AbstractApplicationContext context;
	private IDBConnection dbConnection;
	private PojoFactory pojoFactory;

	@Override
	public List<ITemple> getTempleList() {

		dbConnection = (IDBConnection) context
				.getBean(BeanConstants.DB_CONNECTION);
		pojoFactory = (PojoFactory) context.getBean(BeanConstants.POJO_FACTORY);

		List<ITemple> templeList = new ArrayList<ITemple>();

		ResultSet results = dbConnection.getResultSet(DBConstants.TABLE_TEMPLE);
		
		if (results != null) {
			for (Row row : results) {
				ITemple temple = pojoFactory.getTemplePojo();
				temple.setGod(row.getString(DBConstants.TABLE_TEMPLE_GOD));
				temple.setDistrict(row
						.getString(DBConstants.TABLE_TEMPLE_DISTRICT));
				temple.setPlace(row.getString(DBConstants.TABLE_TEMPLE_PLACE));
				temple.setState(row.getString(DBConstants.TABLE_TEMPLE_STATE));
				templeList.add(temple);
			}
		}
		return templeList;

	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.context = (AbstractApplicationContext) applicationContext;
	}
}
