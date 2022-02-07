package com.github.thelifesnak3.accenture.exception.badrequest;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {
    @Override
    public Response toResponse(BadRequestException e) {
        BadRequestExceptionResponse response = new BadRequestExceptionResponse(e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}
