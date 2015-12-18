package com.shridarshan.in.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shridarshan.in.model.Temple;
import com.shridarshan.in.services.TempleService;

@Path("/pilgrimages")
public class PilgrimResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Temple> getPligrimPlaces() {

		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"beans.xml");
		context.registerShutdownHook();
		
		TempleService templeService = (TempleService) context.getBean("templeservice");

		return templeService.getTempleList(context);
	}
}
