package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class IOTThing extends Hardware {

	private final UUID ID;
	private List<Device> devices;
	private final int MAX_DEVICES = 2;
	private final String UUID_NAME = "Thing";
	private static int counter = 0;

	public IOTThing() {
		super();
		this.ID = genarateUUID();
		devices = new ArrayList<Device>();
	}

	public IOTThing(Type type, String model, String manufacturer) {
		super(type, model, manufacturer);
		this.ID = genarateUUID();
		devices = new ArrayList<Device>();
	}

	// TODO fix the bug
	private synchronized UUID genarateUUID() {
		String name = UUID_NAME + counter++;
		return UUID.nameUUIDFromBytes(name.getBytes());
	}

	public UUID getID() {
		return ID;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public void addDevice(Device device) {
		devices.add(device);
	}

	public void simulateInventoryChange() {
		Random random = new Random();
		boolean toAdd = random.nextBoolean();
		int numOFDevices;

		if (toAdd) {
			numOFDevices = 1 + random.nextInt(MAX_DEVICES);
			for (int i = 0; i < numOFDevices; i++) {
				devices.add(new Device());
				System.out.println("added");
			}
		} else {
			numOFDevices = 1 + random.nextInt(devices.size() - 1);
			for (int i = 0; i < numOFDevices; i++) {
				devices.remove(i);
				System.out.println("removed");
			}
		}

		devices.forEach(Device::simulateReading);
	}

	@Override
	public String toString() {
		return "IOTThing: [ID=" + ID + "] - " + super.toString() + ", devices=" + devices + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IOTThing other = (IOTThing) obj;
		return Objects.equals(ID, other.ID);
	}
}
