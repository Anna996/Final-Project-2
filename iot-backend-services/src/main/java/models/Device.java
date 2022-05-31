package models;

import java.util.Random;

public class Device extends Hardware {

	private double reading;

	public Device() {
		super();
		setReading(0);
	}

	public Device(Type type, String model, String manufacturer) {
		super(type, model, manufacturer);
		setReading(0);
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
		return "Device [reading=" + reading + "]";
	}
}
