package com.shridarshan.in.resources;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.shridarshan.in.pojo.ITemple;
import com.shridarshan.in.services.ITempleService;
import com.shridarshan.in.util.BeanConstants;
import com.shridarshan.in.util.LogConstants;

@Path("/pilgrimages")
public class PilgrimResource {
	private AbstractApplicationContext context;
	private ITempleService templeService;
	private static Logger LOGGER = LoggerFactory.getLogger(PilgrimResource.class);

	
	public PilgrimResource(){
		LOGGER.debug("Initializing | {}", PilgrimResource.class.getName());
		try {
			context = new ClassPathXmlApplicationContext(
					BeanConstants.SERVICES_BEAN_FILE);
		} catch (BeansException e) {
			LOGGER.error(LogConstants.MARKER_FATAL, "Failed to load conext | {}", e.getMessage());
		}
		context.registerShutdownHook();
		LOGGER.debug("Initialized | {}", PilgrimResource.class.getName());

	}
	
/*	@Context annotation allows you to inject instances of

	javax.ws.rs.core.HttpHeaders,
	javax.ws.rs.core.UriInfo,
	javax.ws.rs.core.Request,
	javax.servlet.HttpServletRequest,
	javax.servlet.HttpServletResponse,
	javax.servlet.ServletConfig,
	javax.servlet.ServletContext, and
	javax.ws.rs.core.SecurityContext objects*/
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ITemple> getPligrimPlaces(@Context UriInfo uriInfo, @Context HttpServletRequest requestContext, @Context SecurityContext securityContext) {
		
		String incomingIP = requestContext.getRemoteAddr();
		LOGGER.info("Processing | GET | getPligrimPlaces | Remote Host | {}", incomingIP);
		templeService = (ITempleService) context.getBean(BeanConstants.TEMPLE_SERVICE);
		List<ITemple> templeList = templeService.getTempleList();
		LOGGER.info("Processed | GET | getPligrimPlaces | Remote Host | {}", incomingIP);
		
		return templeList;
	}
}
