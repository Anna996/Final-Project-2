package models;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Device extends Hardware {

	private final UUID ID;
	private double reading;
	private final String UUID_NAME = "Device";
	private static int counter = 0;

	public Device() {
		super();
		this.ID = genarateUUID();
		setReading(0);
	}

	public Device(Type type, String model, String manufacturer) {
		super(type, model, manufacturer);
		this.ID = genarateUUID();
		setReading(0);
	}

	// TODO fix the bug
	private synchronized UUID genarateUUID() {
		String name = UUID_NAME + counter++;
		return UUID.nameUUIDFromBytes(name.getBytes());
	}

	public UUID getID() {
		return ID;
	}

	public double getReading() {
		return reading;
	}

	public void setReading(double reading) {
		this.reading = reading;
	}

	public void simulateReading() {
		setReading(new Random().nextDouble());
	}

	@Override
	public String toString() {
		return "Device: [ID=" + ID + "] - " + super.toString() + ",[ reading=" + reading + "]";
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
		Device other = (Device) obj;
		return Objects.equals(ID, other.ID);
	}
}
