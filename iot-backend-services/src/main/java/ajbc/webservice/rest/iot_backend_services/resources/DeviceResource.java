package ajbc.webservice.rest.iot_backend_services.resources;

import java.util.List;

import ajbc.webservice.rest.iot_backend_services.db_services.DBService;
import ajbc.webservice.rest.iot_backend_services.filter_beans.FilterBean;
import ajbc.webservice.rest.iot_backend_services.models.Device;
import ajbc.webservice.rest.iot_backend_services.models.Hardware;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/devices")
public class DeviceResource {
	private DBService dbService;

	public DeviceResource() {
		this.dbService = new DBService();
	}

	@GET
	public Response getAllDevices(@QueryParam("thing-id") String id, @BeanParam FilterBean filter) {
		List<Device> devicesByThingId = dbService.getDevicesByThingId(id);
		List<? extends Hardware> devices = dbService.getListByParameters(devicesByThingId, filter);
		return Response.ok().entity(devices).build();
	}

	// starts with id: Device5 (0-4 are default and not part of the map)
	@GET
	@Path("/{id}")
	public Response getDeviceById(@PathParam("id") String UUIDName) {
		Device device = dbService.getDeviceByID(UUIDName);
		return Response.ok().entity(device).build();
	}
}
