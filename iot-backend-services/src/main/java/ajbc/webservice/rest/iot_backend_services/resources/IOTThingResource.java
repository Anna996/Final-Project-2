package resources;

import java.util.List;
import java.util.stream.Collectors;

import db_services.DBService;
import filter_beans.FilterBean;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Hardware;
import models.IOTThing;
import models.Type;

@Path("things")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class IOTThingResource {
	private DBService dbService;

	public IOTThingResource() {
		this.dbService = new DBService();
		;
	}

	@GET
	public Response getAllThings(@BeanParam FilterBean filter) {
		List<? extends Hardware> things =  dbService.getListByParameters(dbService.getAllThings(), filter);
		return Response.ok().entity(things).build();
	}

	@GET
	@Path("/{id}")
	public Response getThingById(@PathParam("id") String UUIDName) {
		IOTThing thing = dbService.getThingById(UUIDName);
		return Response.ok().entity(thing).build();
	}
}
