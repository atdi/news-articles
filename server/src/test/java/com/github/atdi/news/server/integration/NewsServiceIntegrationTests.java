package com.github.atdi.news.server.integration;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.model.Author;
import com.github.atdi.news.server.Bootstrap;
import com.github.atdi.news.server.util.JacksonContextResolver;
import org.mockito.internal.util.collections.Sets;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.data.domain.Page;
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
import java.util.Set;
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

    private Author lastAuthor;

    private String lastArticleId;

    private String lastArticleUri;

    @BeforeClass
    public void setUp() throws Exception {
        client = ClientBuilder.newClient().register(JacksonContextResolver.class);
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

    @Test(dependsOnMethods = { "saveAuthorSuccess" })
    public void getAuthor() {
        WebTarget webTarget = client.target(lastAuthorURI);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        assertEquals(response.getStatus(), 200);
        lastAuthor = response.readEntity(Author.class);
        assertEquals(lastAuthor.getId(), lastAuthorId);
    }

    @Test
    public void createArticle() {
        WebTarget webTarget = client.target(INTEGRATION_TESTS_URL + "/article");
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(null);
        assertEquals(response.getStatus(), 201);
        lastArticleId = response.readEntity(String.class);
        lastArticleUri = response.getHeaderString(HttpHeaders.LOCATION);
        assertTrue(lastArticleUri.endsWith(lastArticleId));
    }

    @Test(dependsOnMethods = { "createArticle", "getAuthor" })
    public void saveArticle() {
        WebTarget webTarget = client.target(lastArticleUri);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .put(Entity.entity(Article.builder()
                        .id(lastArticleId)
                        .header("header")
                        .description("short")
                        .text("text")
                        .keywords(Sets.newSet("java", "php"))
                        //.publishDate(LocalDateTime.now())
                        .authors(Sets.newSet(lastAuthor))
                        .build(), MediaType.APPLICATION_JSON_TYPE));
        assertEquals(response.getStatus(), 200);
        Article article = response.readEntity(Article.class);
        assertEquals(article.getId(), lastArticleId);
        Set<Author> authors = article.getAuthors();
        assertTrue(authors.contains(lastAuthor));
    }

    @Test(dependsOnMethods = { "saveArticle" })
    public void getArticleById() {
        WebTarget webTarget = client.target(lastArticleUri);
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        assertEquals(response.getStatus(), 200);
        Article article = response.readEntity(Article.class);
        assertEquals(article.getId(), lastArticleId);
    }

    @Test(dependsOnMethods = { "saveArticle" })
    public void getArticlesByAuthorId() {
        WebTarget webTarget = client.target(INTEGRATION_TESTS_URL + "/article/author/" + lastAuthorId);
        Response response = webTarget.queryParam("page", 0)
                .queryParam("size", 10)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get();
        assertEquals(response.getStatus(), 200);
        //Page<Article> page = (Page<Article>) response.getEntity();
        //assertEquals(page.getSize(), 1);
    }

}
