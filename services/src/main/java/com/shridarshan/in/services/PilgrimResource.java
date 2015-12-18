package com.shridarshan.in.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/pilgrimages")
public class PilgrimResource {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getPligrimPlaces(){
		return "SHRI KRISHNA";
	}
}
