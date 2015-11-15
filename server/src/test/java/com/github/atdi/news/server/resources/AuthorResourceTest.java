package com.github.atdi.news.server.resources;

import com.github.atdi.news.model.Author;
import com.github.atdi.news.server.exceptions.NewsException;
import com.github.atdi.news.server.services.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by aurelavramescu on 13/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorResourceTest {

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorResource authorResource;

    @Test
    public void testCreate() throws Exception {
        String uuid = UUID.randomUUID().toString();
        when(authorService.generateId()).thenReturn(uuid);
        Response response = authorResource.create();
        String retUUID = (String) response.getEntity();
        assertEquals(uuid, retUUID);
        String location = response.getHeaderString(HttpHeaders.LOCATION);
        assertTrue(location.contains(uuid));
        assertTrue(location.contains("author"));
    }

    @Test
    public void testSave() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Author author = Author.builder().id(uuid)
                .firstName("Max")
                .lastName("Krieger")
                .build();
        when(authorService.save(author)).thenReturn(author);
        Response response = authorResource.save(uuid, author);
        Author savedAuthor = (Author) response.getEntity();
        assertEquals(author, savedAuthor);
    }

    @Test(expected = NewsException.class)
    public void testSaveInvalidUUID() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Author author = Author.builder().id(uuid)
                .firstName("Max")
                .lastName("Krieger")
                .build();
        when(authorService.save(author)).thenReturn(author);
        Response response = authorResource.save("invaliduuid", author);
    }

    @Test(expected = NewsException.class)
    public void testSaveNotFound() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Author author = Author.builder().id(uuid)
                .firstName("Max")
                .lastName("Krieger")
                .build();
        when(authorService.save(author)).thenThrow(NullPointerException.class);
        Response response = authorResource.save(UUID.randomUUID().toString(),
                author);
    }

    @Test
    public void testGetById() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Author author = Author.builder().id(uuid)
                .firstName("Max")
                .lastName("Krieger")
                .build();
        when(authorService.find(uuid)).thenReturn(author);
        Response response = authorResource.getById(uuid);
        Author retrievedAuthor = (Author) response.getEntity();
        assertEquals(author, retrievedAuthor);
    }

    @Test(expected = NewsException.class)
    public void testGetByIdInvalidUUID() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Response response = authorResource.getById("invaliduuid");
    }

    @Test(expected = NewsException.class)
    public void testByIdNotFound() throws Exception {
        String uuid = UUID.randomUUID().toString();
        Response response = authorResource.getById(uuid);
    }
}