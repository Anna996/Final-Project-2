package ajbc.webservice.rest.iot_backend_services.models;

public abstract class Hardware {

	private Type type;
	private String model;
	private String manufacturer;

	public Hardware() {
	}

	public Hardware(Type type, String model, String manufacturer) {
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

	@Override
	public String toString() {
		return "Hardware [type=" + type + ", model=" + model + ", manufacturer=" + manufacturer + "]";
	}
}
