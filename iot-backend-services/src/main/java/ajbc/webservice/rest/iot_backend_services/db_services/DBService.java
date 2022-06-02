package ajbc.webservice.rest.iot_backend_services.db_services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import ajbc.webservice.rest.iot_backend_services.database.DBMock;
import ajbc.webservice.rest.iot_backend_services.exceptions.MissingDataException;
import ajbc.webservice.rest.iot_backend_services.filter_beans.FilterBean;
import ajbc.webservice.rest.iot_backend_services.models.Device;
import ajbc.webservice.rest.iot_backend_services.models.Hardware;
import ajbc.webservice.rest.iot_backend_services.models.IOTThing;
import ajbc.webservice.rest.iot_backend_services.models.Type;

public class DBService {

	private DBMock database;
	private Map<UUID, IOTThing> things;
	private Map<UUID, Device> devices;
	private Random random;
	private List<Device> defaultDevices;

	public DBService() {
		database = DBMock.getInstance();
		updateThings();
		updateDevices();
		random = new Random();
		defaultDevices = database.getDefaultDevices();
	}

	private void updateThings() {
		things = database.getThings();
	}

	private void updateDevices() {
		devices = database.getDevices();
	}
	
	public Device getRandomDevice() {
		return defaultDevices.get(random.nextInt(defaultDevices.size()));
	}

	public void updateDB(IOTThing newThing) throws RuntimeException {
		IOTThing prevThing = things.get(newThing.getID());

		if (prevThing == null) {
			throw new RuntimeException("");
		}

		newThing.getDevices().forEach(device -> {
			if (!prevThing.getDevices().contains(device)) {
				database.addDevice(device);
				System.out.println("Added device " + device.getID() + " to database....................");
			}
			updateDevices();
		});

		prevThing.getDevices().forEach(originalDevice -> {
			if (!newThing.getDevices().contains(originalDevice)) {
				database.removeDevice(originalDevice);
				System.out.println("Removed device " + originalDevice.getID() + " from database....................");
			}
			updateDevices();
		});

		database.replaceThing(newThing);
		updateThings();
	}

	public List<IOTThing> getAllThings() {
		return new ArrayList<IOTThing>(things.values());
	}

	private UUID parseID(String id) {
		return UUID.nameUUIDFromBytes(id.getBytes());
	}

	public IOTThing getThingById(String id) throws MissingDataException {
		IOTThing thing = things.get(parseID(id));

		if (thing == null) {
			throw new MissingDataException("IOTThing", id);
		}

		return thing;
	}

	public List<Device> getAllDevices() {
		return new ArrayList<Device>(devices.values());
	}

	public Device getDeviceByID(String id) throws MissingDataException {
		Device device = devices.get(parseID(id));

		if (device == null) {
			throw new MissingDataException("Device", id);
		}

		return device;
	}

	public List<? extends Hardware> getListByParameters(List<? extends Hardware> list, FilterBean filter) {

		if (filter.getType() != null) {
			Type type = filter.getType();

			list = list.stream().filter(hardware -> hardware.getType() != null)
					.filter(hardware -> hardware.getType().equals(type)).collect(Collectors.toList());
		}

		if (filter.getModel() != null) {
			String model = filter.getModel();

			list = list.stream().filter(hardware -> hardware.getModel() != null)
					.filter(hardware -> hardware.getModel().equals(model)).collect(Collectors.toList());
		}
		if (filter.getManufacturer() != null) {
			String manufacturer = filter.getManufacturer();

			list = list.stream().filter(hardware -> hardware.getManufacturer() != null)
					.filter(hardware -> hardware.getManufacturer().equals(manufacturer)).collect(Collectors.toList());
		}

		return list;
	}

	public List<Device> getDevicesByThingId(String id) {

		if (id == null) {
			return getAllDevices();
		}

		return getThingById(id).getDevices();
	}
}
