package ajbc.webservice.rest.iot_backend_services.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import ajbc.webservice.rest.iot_backend_services.models.Device;
import ajbc.webservice.rest.iot_backend_services.models.IOTThing;
import ajbc.webservice.rest.iot_backend_services.models.Type;

public class DBMock {
	private static DBMock database = null;
	private Map<UUID, IOTThing> things;
	private Map<UUID, Device> devices;
	private List<Device> defaultDevices;

	private DBMock() {
		seedDatabase();
	}

	public static synchronized DBMock getInstance() {
		if (database == null) {
			database = new DBMock();
		}

		return database;
	}

	private void seedDatabase() {

		List<IOTThing> things = new ArrayList<>();
		things.add(new IOTThing(Type.CONTROLLER, "model A", "manufacturer A"));
		things.add(new IOTThing(Type.CONTROLLER, "model A", "manufacturer A"));
		things.add(new IOTThing(Type.CONTROLLER, "model B", "manufacturer A"));
		things.add(new IOTThing(Type.CONTROLLER, "model B", "manufacturer B"));
		things.add(new IOTThing(Type.CONTROLLER, "model C", "manufacturer B"));

		defaultDevices = new ArrayList<>();
		defaultDevices.add(new Device(Type.SENSOR, "model A", "manufacturer A"));
		defaultDevices.add(new Device(Type.SENSOR, "model A", "manufacturer B"));
		defaultDevices.add(new Device(Type.ACTUATOR, "model B", "manufacturer A"));
		defaultDevices.add(new Device(Type.ACTUATOR, "model B", "manufacturer B"));
		defaultDevices.add(new Device(Type.ACTUATOR, "model C", "manufacturer A"));

		List<Device> devices = defaultDevices.stream().map(device -> Device.copyDevice(device))
				.collect(Collectors.toList());

		for (int i = 0; i < things.size(); i++) {
			things.get(i).addDevice(devices.get(i));
		}

		this.things = things.stream().collect(Collectors.toMap(IOTThing::getID, Function.identity()));
		this.devices = devices.stream().collect(Collectors.toMap(Device::getID, Function.identity()));
	}

	public Map<UUID, IOTThing> getThings() {
		return things;
	}

	public Map<UUID, Device> getDevices() {
		return devices;
	}

	public List<Device> getDefaultDevices() {
		return defaultDevices;
	}

	public synchronized void addDevice(Device device) {
		devices.put(device.getID(), device);
	}

	public synchronized void removeDevice(Device device) {
		devices.remove(device.getID());
	}

	public synchronized void replaceThing(IOTThing thing) {
		things.replace(thing.getID(), thing);
	}
	
	public synchronized void addThing(IOTThing thing) {
		things.put(thing.getID(), thing);
	}
}
