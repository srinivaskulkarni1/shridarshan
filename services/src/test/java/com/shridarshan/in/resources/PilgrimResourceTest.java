package com.shridarshan.in.resources;

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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.services.ITempleService;
import com.shridarshan.in.testdata.TestData;
import com.shridarshan.in.util.BeanConstants;

@RunWith(MockitoJUnitRunner.class)
public class PilgrimResourceTest {

	@Mock
	AbstractApplicationContext context;
	
	@Mock
	ITempleService service;
	
	@Autowired
	@InjectMocks
	PilgrimResource pilgrimResource;
	
	@Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testGetPilgrimPlaces(){
		List<ITemple> templeList = TestData.getDummyTempleList();
		
		when(context.getBean(BeanConstants.TEMPLE_SERVICE)).thenReturn(service);
		when(service.getTempleList()).thenReturn(templeList);
		
		assertEquals(pilgrimResource.getPligrimPlaces(), templeList);

		verify(context, times(1)).getBean(BeanConstants.TEMPLE_SERVICE);
		verify(service, timeout(1)).getTempleList();
	}
	
}
