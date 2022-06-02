package tests.ajbc.webservice.rest.iot_backend_services.resources;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import ajbc.webservice.rest.iot_backend_services.resources.DeviceResource;
import jakarta.ws.rs.core.Response;

class DeviceResourceTest {
	private DeviceResource resource;

	@Test
	void testGetAllDevices() {
		Response acutal = resource.getAllDevices(null, null);

		assertEquals(Response.ok(), acutal.getStatus());
	}

	@Test
	void testGetDevicById() {
		fail("Not yet implemented");
	}

}
