package resources;

import java.util.List;

import db_services.DBService;
import filter_beans.FilterBean;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import models.Device;
import models.Hardware;

@Path("/devices")
public class DeviceResource {
	private DBService dbService;

	public DeviceResource() {
		this.dbService = new DBService();
		;
	}

	@GET
	public Response getAllDevices(@QueryParam("thing-id") String id, @BeanParam FilterBean filter) {
		List<Device> devicesByThingId = dbService.getDevicesByThingId(id);
		List<? extends Hardware> devices = dbService.getListByParameters(devicesByThingId, filter);
		return Response.ok().entity(devices).build();
	}

	@GET
	@Path("/{id}")
	public Response getDeviceById(@PathParam("id") String uuidNAME) {
		Device device = dbService.getDeviceByID(uuidNAME);
		return Response.ok().entity(device).build();
	}
}
