package com.pas.exception.mapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {

    public Response toResponse(EntityNotFoundException exception) {
        log.info("An error occurred", exception);
        return Response.status(404).entity(exception.getMessage()).type("application/json").build();
    }

}
