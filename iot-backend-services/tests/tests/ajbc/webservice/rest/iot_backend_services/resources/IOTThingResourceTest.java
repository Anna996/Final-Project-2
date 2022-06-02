package tests.ajbc.webservice.rest.iot_backend_services.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;
import ajbc.webservice.rest.iot_backend_services.filter_beans.FilterBean;
import ajbc.webservice.rest.iot_backend_services.models.IOTThing;
import ajbc.webservice.rest.iot_backend_services.models.Type;
import ajbc.webservice.rest.iot_backend_services.resources.IOTThingResource;
import jakarta.ws.rs.core.Response;

class IOTThingResourceTest {
	private IOTThingResource resource;
	private List<IOTThing> things;

	public IOTThingResourceTest() {
		this.resource = new IOTThingResource();
		things = new ArrayList<IOTThing>(DBMock.getInstance().getThings().values());
	}

	@Test
	void testGetAllThings() {
		FilterBean filterBean = new FilterBean();
		
		Response acutal = resource.getAllThings(filterBean);
		assertEquals(200, acutal.getStatus());
		assertEquals(things, acutal.getEntity());
		
		filterBean.setType(Type.CONTROLLER);
		acutal = resource.getAllThings(filterBean);
		assertEquals(200, acutal.getStatus());
		assertEquals(things, acutal.getEntity());
	}

	@Test
	void testGetThingById() {
//		fail("Not yet implemented");
	}
}
