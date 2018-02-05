package org.newbieApps.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.newbieApps.messenger.model.ErrorMessage;
/*@Provider annotation registers mapper with jax-rs so that it can be checked when exception scenario occurs.*/
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage= new ErrorMessage(ex.getMessage(),404,"http://google.co.in");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}
