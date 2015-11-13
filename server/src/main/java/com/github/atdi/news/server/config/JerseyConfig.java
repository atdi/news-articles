package com.github.atdi.news.server.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.github.atdi.news.server.util.JacksonContextResolver;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationError;
import org.springframework.stereotype.Component;

import javax.ws.rs.ApplicationPath;

/**
 * Jersey configuration.
 *
 * Created by aurelavramescu on 13/11/15.
 */
@Component
@ApplicationPath("api")
public class JerseyConfig extends ResourceConfig {

    /**
     * Register jersey resources.
     */
    public JerseyConfig() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        property(ServerProperties.
                BV_DISABLE_VALIDATE_ON_EXECUTABLE_OVERRIDE_CHECK, true);
        register(ValidationError.class);
        register(JacksonJaxbJsonProvider.class);
        register(JacksonContextResolver.class);
        packages("com.github.atdi.news.server.resources");
    }
}
