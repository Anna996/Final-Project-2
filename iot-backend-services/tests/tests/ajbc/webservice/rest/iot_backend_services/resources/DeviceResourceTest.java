package tests.ajbc.webservice.rest.iot_backend_services.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;
import ajbc.webservice.rest.iot_backend_services.exceptions.MissingDataException;
import ajbc.webservice.rest.iot_backend_services.filter_beans.FilterBean;
import ajbc.webservice.rest.iot_backend_services.models.Device;
import ajbc.webservice.rest.iot_backend_services.models.Type;
import ajbc.webservice.rest.iot_backend_services.resources.DeviceResource;
import jakarta.ws.rs.core.Response;

class DeviceResourceTest {
	private DeviceResource resource;
	private List<Device> devices;
	
	public DeviceResourceTest() {
		this.resource = new DeviceResource();
		devices= new ArrayList<Device>(DBMock.getInstance().getDevices().values());
	}

	@Test
	void testGetAllDevices() {
		FilterBean filterBean = new FilterBean();
		
		Response acutal = resource.getAllDevices(null, filterBean);
		assertEquals(200, acutal.getStatus());
		assertEquals(devices, acutal.getEntity());
		
		acutal = resource.getAllDevices("Thing0", filterBean);
		assertEquals(200, acutal.getStatus());
		List<Device> devices = (List<Device>) acutal.getEntity();
		assertEquals(Type.SENSOR, devices.get(0).getType());
		assertEquals("model A", devices.get(0).getModel());
		assertEquals("manufacturer A", devices.get(0).getManufacturer());
		
		filterBean.setModel("model A");
		acutal = resource.getAllDevices(null, filterBean);
		devices = (List<Device>) acutal.getEntity();
		assertTrue(devices.size() == 2);
		assertEquals("SENSOR", devices.get(0).getType().toString());
		assertEquals("SENSOR", devices.get(1).getType().toString());
		
		assertEquals( "manufacturer B", devices.get(0).getManufacturer());
		assertEquals( "manufacturer A", devices.get(1).getManufacturer());
	}

	// starts with id: Device5 (0-4 are default and not part of the map)
	@Test
	void testGetDevicById() {
		Response acutalGood = resource.getDeviceById("Device5");
		assertEquals(200, acutalGood.getStatus());
		
		Device device = (Device) acutalGood.getEntity();
		assertEquals(Type.SENSOR, device.getType());
		assertEquals("model A", device.getModel());
		assertEquals("manufacturer A", device.getManufacturer());
		
		assertThrows( MissingDataException.class, ()-> resource.getDeviceById("Device20"));
	}
}
