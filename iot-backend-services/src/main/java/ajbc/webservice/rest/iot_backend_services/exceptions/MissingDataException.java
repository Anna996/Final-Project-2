package ajbc.webservice.rest.iot_backend_services.exceptions;

public class MissingDataException extends RuntimeException {

	private static final long serialVersionUID = 3336859843520318039L;
	private static String message = "There is no such element ";
	private String objType;
	private String nameId;

	public MissingDataException() {
		super(message);
	}

	public MissingDataException(String objType , String nameId) {
		super(message + objType + " with ID " + nameId);
		this.objType = objType;
		this.nameId = nameId;
	}

	public String getObjType() {
		return objType;
	}

	public String getNameId() {
		return nameId;
	}
}
