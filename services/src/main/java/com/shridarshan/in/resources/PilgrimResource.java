package com.shridarshan.in.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.services.ITempleService;
import com.shridarshan.in.util.BeanConstants;

@Path("/pilgrimages")
public class PilgrimResource {
	
	private AbstractApplicationContext context;
	private ITempleService templeService;
	
	public PilgrimResource(){
		context = new ClassPathXmlApplicationContext(
				BeanConstants.SERVICES_BEAN_FILE);
		context.registerShutdownHook();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ITemple> getPligrimPlaces() {
		templeService = (ITempleService) context.getBean(BeanConstants.TEMPLE_SERVICE);
		return templeService.getTempleList();
	}
}
