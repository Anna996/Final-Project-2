package ajbc.webservice.rest.iot_backend_services.exceptions;

import ajbc.webservice.rest.iot_backend_services.models.ErrorMessage;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MissingDataExceptionMapper implements ExceptionMapper<MissingDataException>{

	@Override
	public Response toResponse(MissingDataException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getObjType(), exception.getNameId(), exception.getMessage(),Status.NOT_FOUND);
		return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
	}
}
