package com.github.atdi.news.server.exceptions;

import com.github.atdi.news.model.HttpError;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * News service web application exception.
 * <p>
 * Created by aurelavramescu on 13/11/15.
 */
public class NewsException extends WebApplicationException {

    /**
     * Constructor.
     *
     * @param e exception
     */
    public NewsException(final Throwable e) {
        super(e, Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(HttpError.builder()
                        .message(e.getMessage())
                        .status(Response.Status.INTERNAL_SERVER_ERROR
                                .getStatusCode())
                        .build())
                .type(MediaType.APPLICATION_JSON_TYPE)
                .build());
    }

    /**
     * Constructor.
     *
     * @param message error message
     * @param e       exception
     */
    public NewsException(final String message, final Throwable e) {
        super(e, Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(HttpError.builder()
                        .message(message)
                        .status(Response.Status.INTERNAL_SERVER_ERROR
                                .getStatusCode())
                        .exception(e.getClass().getSimpleName())
                        .build()).type(MediaType.APPLICATION_JSON_TYPE)
                .build());
    }

    /**
     * Constructor.
     *
     * @param message error message
     * @param status  response status
     * @param e       exception
     */
    public NewsException(final String message,
                         final Response.Status status,
                         final Throwable e) {
        super(e, Response.status(status).
                entity(HttpError.builder()
                        .message(message)
                        .status(status.getStatusCode())
                        .exception(e.getClass().getSimpleName())
                        .build()).type(MediaType.APPLICATION_JSON_TYPE)
                .build());
    }

    /**
     * Constructor.
     *
     * @param message error message
     */
    public NewsException(final String message) {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                entity(HttpError.builder()
                        .message(message)
                        .status(Response.Status.INTERNAL_SERVER_ERROR
                                .getStatusCode())
                        .build()).type(MediaType.APPLICATION_JSON_TYPE)
                .build());
    }

    /**
     * Constructor.
     *
     * @param message error message
     * @param status  response status
     */
    public NewsException(final String message, final Response.Status status) {
        super(Response.status(status).
                entity(HttpError.builder()
                        .message(message)
                        .status(status.getStatusCode())
                        .build()).type(MediaType.APPLICATION_JSON_TYPE)
                .build());
    }
}
