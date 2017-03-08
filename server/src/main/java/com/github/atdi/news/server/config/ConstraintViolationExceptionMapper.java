package com.github.atdi.news.server.config;

import com.github.atdi.news.model.HttpError;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Created by aurel on 08/03/17.
 */
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response
                .status(400)
                .entity(HttpError.builder()
                        .exception(ConstraintViolationException.class.getSimpleName())
                        .message(exception.getConstraintViolations().iterator().next().getMessage())
                        .status(400)
                        .build())
                .build();
    }
}
