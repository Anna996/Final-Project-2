package db_services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import database.DBMock;
import filter_beans.FilterBean;
import models.Device;
import models.Hardware;
import models.IOTThing;
import models.Type;

public class DBService {

	private DBMock database;
	private Map<UUID, IOTThing> things;
	private Map<UUID, Device> devices;

	public DBService() {
		database = DBMock.getInstance();
		updateThings();
		updateDevices();
	}

	private void updateThings() {
		things = database.getThings();
	}

	private void updateDevices() {
		devices = database.getDevices();
	}

	// TODO handle exception
	// TODO add lock
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

	public IOTThing getThingById(String id) throws RuntimeException {
		IOTThing thing = things.get(parseID(id));

		if (thing == null) {
			throw new RuntimeException("");
		}
		
		return thing;
	}

	public List<Device> getAllDevices() {
		return new ArrayList<Device>(devices.values());
	}

	public Device getDeviceByID(String id) {
		Device device = devices.get(parseID(id));

		if (device == null) {
			throw new RuntimeException();
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

	// TODO Exception
	public List<Device> getDevicesByThingId(String id) {

		if (id == null) {
			return getAllDevices();
		}

		return things.get(parseID(id)).getDevices();
	}
}
