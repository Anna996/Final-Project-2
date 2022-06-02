package ajbc.webservice.rest.iot_backend_services.models;

import jakarta.ws.rs.core.Response.Status;

public class ErrorMessage {
	private String objType;
	private String nameId;
	private String message;
	private Status status;

	public ErrorMessage(String objType, String nameId, String message, Status status) {
		this.objType = objType;
		this.nameId = nameId;
		this.message = message;
		this.status = status;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
