package models;

import java.util.UUID;

public abstract class Hardware {

	private final UUID ID;
	private Type type;
	private String model;
	private String manufacturer;

	public Hardware() {
		this.ID = UUID.randomUUID();
	}

	public Hardware(Type type, String model, String manufacturer) {
		this.ID = UUID.randomUUID();
		this.type = type;
		this.model = model;
		this.manufacturer = manufacturer;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public UUID getID() {
		return ID;
	}

	@Override
	public String toString() {
		return "Hardware [ID=" + ID + ", type=" + type + ", model=" + model + ", manufacturer=" + manufacturer + "]";
	}
}
