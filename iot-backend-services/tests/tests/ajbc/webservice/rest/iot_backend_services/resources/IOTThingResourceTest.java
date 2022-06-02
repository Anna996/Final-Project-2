package tests.ajbc.webservice.rest.iot_backend_services.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;
import ajbc.webservice.rest.iot_backend_services.exceptions.MissingDataException;
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
		
		filterBean.setType(Type.SENSOR);
		acutal = resource.getAllThings(filterBean);
		assertEquals(200, acutal.getStatus());
		assertEquals(new ArrayList<>(), acutal.getEntity());
	}

	@Test
	void testGetThingById() {
		Response acutalGood = resource.getThingById("Thing0");
		assertEquals(200, acutalGood.getStatus());
		
		IOTThing thing = (IOTThing) acutalGood.getEntity();
		assertEquals(Type.CONTROLLER, thing.getType());
		assertEquals("model A", thing.getModel());
		assertEquals("manufacturer A", thing.getManufacturer());
		
		assertThrows( MissingDataException.class, ()-> resource.getThingById("Thing8"));
	}
}
