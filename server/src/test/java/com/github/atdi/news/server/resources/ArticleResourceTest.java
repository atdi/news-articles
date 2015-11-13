package com.github.atdi.news.server.resources;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.exceptions.NewsException;
import com.github.atdi.news.server.services.ArticleSearchService;
import com.github.atdi.news.server.services.ArticleService;
import com.github.atdi.news.server.services.repositories.ArticleJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by aurelavramescu on 13/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ArticleResourceTest {

    @Mock
    private ArticleJpaRepository articleJpaRepository;

    @Mock
    private ArticleSearchService articleSearchService;

    @InjectMocks
    private ArticleService articleService;

    private ArticleResource articleResource;

    @Before
    public void setUp() throws Exception {
        articleResource = new ArticleResource(articleService);
    }


    @Test
    public void testCreateArticle() throws Exception {
        Response response = articleResource.create();
        String id = (String) response.getEntity();
        String location = response.getHeaderString(HttpHeaders.LOCATION);
        assertTrue(location.contains(id));
    }

    @Test
    public void testSaveArticle() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = Article.builder()
                .id(id)
                .description("description")
                .header("header")
                .text("text")
                .keywords(Sets.newSet("java", "php"))
                .build();
        when(articleJpaRepository.save(article)).thenReturn(article);
        Response response = articleResource.save(id, article);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Article savedArticle = (Article) response.getEntity();
        assertEquals(article, savedArticle);
    }

    @Test(expected = NewsException.class)
    public void testSaveArticleNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = Article.builder()
                .id(id)
                .description("description")
                .header("header")
                .text("text")
                .keywords(Sets.newSet("java", "php"))
                .build();
        when(articleJpaRepository.save(article)).thenReturn(article);
        Response response = articleResource.save(UUID.randomUUID().toString(), article);
    }

    @Test(expected = NewsException.class)
    public void testSaveArticleInvalidUUID() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = Article.builder()
                .id(id)
                .description("description")
                .header("header")
                .text("text")
                .keywords(Sets.newSet("java", "php"))
                .build();
        when(articleJpaRepository.save(article)).thenReturn(article);
        Response response = articleResource.save("invaliduuid", article);
    }

    @Test
    public void testGetArticle() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = Article.builder()
                .id(id)
                .description("description")
                .header("header")
                .text("text")
                .keywords(Sets.newSet("java", "php"))
                .build();
        when(articleJpaRepository.findOne(id)).thenReturn(article);
        Response response = articleResource.getById(id);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Article retrievedArticle = (Article) response.getEntity();
        assertEquals(article, retrievedArticle);
    }

    @Test(expected = NewsException.class)
    public void testGetArticleNotFound() throws Exception {
        String id = UUID.randomUUID().toString();
        when(articleJpaRepository.findOne(id)).thenReturn(null);
        Response response = articleResource.getById(id);
    }

    @Test(expected = NewsException.class)
    public void testGetArticleInvalidUUID() throws Exception {
        String id = "invaliduuid";
        when(articleJpaRepository.findOne(id)).thenThrow(NullPointerException.class);
        Response response = articleResource.getById(id);
    }

    @Test
    public void testGetArticlesByAuthorId() throws Exception {
        String id = UUID.randomUUID().toString();
        Page<Article> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(2L);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);
        when(page.getContent()).thenReturn(Arrays.asList(article1, article2));
        Pageable pageable = new PageRequest(0, 10);
        when(articleSearchService.searchByAuthor(eq(pageable), eq(id))).thenReturn(page);
        Response response = articleResource.getByAuthorId(id, 0, 10);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Page<Article> retrievedArticles = (Page<Article>) response.getEntity();
        assertEquals(2, retrievedArticles.getContent().size());
    }
}