package com.shridarshan.in.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;

import com.shridarshan.in.data.IDataLoader;
import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.testdata.TestData;
import com.shridarshan.in.util.BeanConstants;

@RunWith(MockitoJUnitRunner.class)
public class TempleServiceTest {

	@Mock
	AbstractApplicationContext context;
	
	@Mock
	IDataLoader dataLoader;
	
	@Autowired
	@InjectMocks
	TempleService service;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testGetTempleList() {
		List<ITemple> templeList = TestData.getDummyTempleList();
		
		when(context.getBean(BeanConstants.DATA_LOADER)).thenReturn(dataLoader);
		when(dataLoader.getTempleList()).thenReturn(templeList);
		
		assertEquals(service.getTempleList(), templeList);

		verify(context, times(1)).getBean(BeanConstants.DATA_LOADER);
		verify(dataLoader, timeout(1)).getTempleList();
	}

}
