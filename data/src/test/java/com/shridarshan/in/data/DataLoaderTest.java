package com.shridarshan.in.data;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;

import com.datastax.driver.core.ResultSet;
import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.pojo.PojoFactory;
import com.shridarshan.in.util.BeanConstants;
import com.shridarshan.in.util.DBConstants;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class DataLoaderTest {

	@Mock
	AbstractApplicationContext context;
	
	@Mock
	IDBConnection dbConnection;
	
	@Mock
	PojoFactory pojoFactory;
	
	@Autowired
	@InjectMocks
	DataLoader dataLoader;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testGetTempleList(){
		List<ITemple> templeList = new ArrayList<ITemple>();
		ResultSet resultSet = null;
		
		when(context.getBean(BeanConstants.DB_CONNECTION)).thenReturn(dbConnection);
		when(context.getBean(BeanConstants.POJO_FACTORY)).thenReturn(pojoFactory);
		when(dbConnection.getResultSet(DBConstants.TABLE_TEMPLE)).thenReturn(resultSet);
		
		assertEquals(dataLoader.getTempleList(), templeList);
		
		verify(context, times(1)).getBean(BeanConstants.DB_CONNECTION);
		verify(context, times(1)).getBean(BeanConstants.POJO_FACTORY);
		verify(dbConnection, times(1)).getResultSet(DBConstants.TABLE_TEMPLE);
	}
	

}
