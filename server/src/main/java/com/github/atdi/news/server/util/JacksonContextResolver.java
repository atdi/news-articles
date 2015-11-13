package com.github.atdi.news.server.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

import javax.ws.rs.ext.ContextResolver;

/**
 * Jackson context resolver for Java 8 time utilities.
 * <p>
 * Created by aurelavramescu on 13/11/15.
 */
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private static final ObjectMapper OBJECT_MAPPER = init();

    @Override
    public ObjectMapper getContext(final Class<?> objectType) {
        return OBJECT_MAPPER;
    }

    private static ObjectMapper init() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JSR310Module());
        return om;
    }
}
