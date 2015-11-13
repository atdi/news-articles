package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Author;
import com.github.atdi.news.server.services.repositories.AuthorJpaRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Created by aurelavramescu on 13/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {

    @Mock
    private AuthorJpaRepository authorJpaRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    public void testGenerateId() throws Exception {
        String id = authorService.generateId();
        assertNotNull(id);
    }

    @Test
    public void testSave() throws Exception {
        String id = UUID.randomUUID().toString();
        Author author = Author.builder()
                .lastName("Krieger")
                .firstName("Max")
                .id(id).build();
        when(authorJpaRepository.save(author)).thenReturn(author);
        Author savedAuthor = authorService.save(author);
        assertEquals(author, savedAuthor);
    }

    @Test
    public void testFind() throws Exception {
        String id = UUID.randomUUID().toString();
        Author author = Author.builder()
                .lastName("Krieger")
                .firstName("Max")
                .id(id).build();
        when(authorJpaRepository.findOne(id)).thenReturn(author);
        Author saveAuthor = authorService.find(id);
        assertEquals(author, saveAuthor);
    }
}