package com.github.atdi.news.server.integration;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.model.Author;
import com.github.atdi.news.server.Bootstrap;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Created by aurelavramescu on 13/11/15.
 */
@ActiveProfiles("integration")
@SpringApplicationConfiguration(classes = {Bootstrap.class})
@WebIntegrationTest
public class NewsServiceIntegrationTests extends AbstractTestNGSpringContextTests {

    private static final String INTEGRATION_TESTS_URL = "http://localhost:8080/api";
    private Client client;

    private String lastAuthorId;

    private String lastAuthorURI;

    @BeforeClass
    public void setUp() throws Exception {
        client = ClientBuilder.newClient();
    }

    @Test
    public void createAuthor() {
        WebTarget webTarget = client.target(INTEGRATION_TESTS_URL + "/author");
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null);
        assertEquals(response.getStatus(), 201);
        lastAuthorId = response.readEntity(String.class);
        lastAuthorURI = response.getHeaderString(HttpHeaders.LOCATION);
        assertTrue(lastAuthorURI.endsWith(lastAuthorId));
    }

    @Test(dependsOnMethods = { "createAuthor" })
    public void saveAuthorSuccess() {
        WebTarget webTarget = client.target(lastAuthorURI);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(
                        Author.builder().id(lastAuthorId).firstName("Max").lastName("Krieger").build(),
                        MediaType.APPLICATION_JSON_TYPE));
        assertEquals(response.getStatus(), 200);
        Author author = response.readEntity(Author.class);
        assertEquals(author.getId(), lastAuthorId);
    }

    @Test(dependsOnMethods = { "createAuthor" })
    public void saveAuthorBadRequest() {
        WebTarget webTarget = client.target(lastAuthorURI);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(
                        Author.builder().id(lastAuthorId).lastName("Krieger").build(),
                        MediaType.APPLICATION_JSON_TYPE));
        assertEquals(response.getStatus(), 400);
    }

    @Test(dependsOnMethods = { "createAuthor" })
    public void saveAuthorBadRequestInvalidUUID() {
        WebTarget webTarget = client.target(INTEGRATION_TESTS_URL + "/author/invaliduuid");
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(
                        Author.builder().id(lastAuthorId).lastName("Krieger").build(),
                        MediaType.APPLICATION_JSON_TYPE));
        assertEquals(response.getStatus(), 400);
    }

    @Test(dependsOnMethods = { "createAuthor" })
    public void saveAuthorNotFound() {
        WebTarget webTarget = client.target(lastAuthorURI);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(
                        Author.builder().id(UUID.randomUUID().toString())
                                .firstName("Max").lastName("Krieger").build(),
                        MediaType.APPLICATION_JSON_TYPE));
        assertEquals(response.getStatus(), 404);
    }

}
