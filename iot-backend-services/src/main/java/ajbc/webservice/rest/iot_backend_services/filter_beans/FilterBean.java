package ajbc.webservice.rest.iot_backend_services.filter_beans;

import ajbc.webservice.rest.iot_backend_services.models.Type;
import jakarta.ws.rs.QueryParam;

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
