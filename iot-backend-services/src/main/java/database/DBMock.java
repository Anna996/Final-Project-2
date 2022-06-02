package database;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import models.Device;
import models.IOTThing;
import models.Type;

public class DBMock {
	private static DBMock database = null;
	private final int NUM_DEVICES_FOR_THNIG = 2;
	private Map<UUID, IOTThing> things;
	private Map<UUID, Device> devices;

	private DBMock() {
		seedThings();
		seedDevices();
	}

	public static synchronized DBMock getInstance() {
		if (database == null) {
			database = new DBMock();
		}

		return database;
	}

	private void seedThings() {

//		List<IOTThing> things = Arrays.asList(
//				new IOTThing(), new IOTThing(), 
//				new IOTThing(), new IOTThing(),
//				new IOTThing(), new IOTThing(), 
//				new IOTThing(), new IOTThing(), 
//				new IOTThing(), new IOTThing());

		List<IOTThing> things = Arrays.asList(new IOTThing(Type.CONTROLLER, "model A", "manufacturer B"),
				new IOTThing());

		this.things = things.stream().collect(Collectors.toMap(IOTThing::getID, Function.identity()));
	}

	private void seedDevices() {

		List<Device> devices = new LinkedList<>();

		things.forEach((id, thing) -> {
			for (int i = 0; i < NUM_DEVICES_FOR_THNIG; i++) {
				Device device = new Device(Type.SENSOR, "model A", "manufacturer B");
				thing.addDevice(device);
				devices.add(device);
			}
		});

		this.devices = devices.stream().collect(Collectors.toMap(Device::getID, Function.identity()));
	}

	public Map<UUID, IOTThing> getThings() {
		return things;
	}

	public Map<UUID, Device> getDevices() {
		return devices;
	}

	public synchronized void addDevice(Device device) {
		devices.put(device.getID(), device);
	}

	public synchronized void removeDevice(Device device) {
		devices.remove(device.getID());
	}
	
	public  synchronized void replaceThing(IOTThing thing) {
		things.replace(thing.getID(), thing);
	}

	public static void main(String[] args) {
		DBMock.getInstance().things.forEach((uuid, thing) -> System.out.println(thing));
	}
}
