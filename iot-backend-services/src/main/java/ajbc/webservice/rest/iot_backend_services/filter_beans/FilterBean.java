package filter_beans;

import jakarta.ws.rs.QueryParam;
import models.Type;

public class FilterBean {

	@QueryParam("type")
	private Type type;
	@QueryParam("model")
	private String model;
	@QueryParam("manufacturer")
	private String manufacturer;

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
}
