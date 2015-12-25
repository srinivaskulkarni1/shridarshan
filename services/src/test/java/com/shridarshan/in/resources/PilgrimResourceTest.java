package com.shridarshan.in.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		
		when(context.getBean(BeanConstants.TEMPLE_SERVICE)).thenReturn(service);
		when(service.getTempleList()).thenReturn(templeList);
		when(request.getRemoteAddr()).thenReturn("127.0.0.1");
		
		assertEquals(pilgrimResource.getPligrimPlaces(null, request, null), templeList);

		verify(context, times(1)).getBean(BeanConstants.TEMPLE_SERVICE);
		verify(service, times(1)).getTempleList();
		verify(request, times(1)).getRemoteAddr();
	}
	
}
