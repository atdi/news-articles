package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.services.repositories.ArticleRepository;
import com.github.atdi.news.server.services.repositories.AuthorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by aurelavramescu on 12/11/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private NewsService newsService;

    @Test
    public void testGenerateId() throws Exception {
        String id = newsService.generateId();
        assertNotNull(id);
    }

    @Test
    public void testSaveArticle() throws Exception {
        Article article = Article.builder()
                .id(newsService.generateId())
                .header("This is a test")
                .description("This is a small article")
                .keywords(Sets.<String>newSet("fire", "water"))
                .text("Content")
                .build();
        when(articleRepository.save(article)).thenReturn(article);
        Article savedInstance = newsService.saveArticle(article);
        assertEquals(article, savedInstance);
    }

    @Test
    public void testDeleteArticle() throws Exception {
        String id = newsService.generateId();
        Article article = Article.builder()
                .id(id)
                .header("This is a test")
                .description("This is a small article")
                .keywords(Sets.<String>newSet("fire", "water"))
                .text("Content")
                .build();
        when(articleRepository.save(article)).thenReturn(article);
        newsService.saveArticle(article);
        newsService.deleteArticle(id);
        Article deletedArticle = newsService.findArticle(id);
        assertNull(deletedArticle);
    }

    @Test
    public void testGetAllArticles() throws Exception {
        Page<Article> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(2L);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);
        when(page.getContent()).
                thenReturn(Arrays.asList(article1,
                        article2));
        Pageable pageable = new PageRequest(0, 10);
        when(articleRepository.findAll(pageable)).thenReturn(page);
        Page<Article> result = newsService.getAllArticles(pageable);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllArticlesPublishedBetween() throws Exception {
        Page<Article> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(2L);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);
        when(page.getContent()).
                thenReturn(Arrays.asList(article1,
                        article2));
        Pageable pageable = new PageRequest(0, 10);
        LocalDateTime startDate = LocalDateTime.
                of(2015, Month.AUGUST, 10, 8, 15);
        LocalDateTime endDate = LocalDateTime.now();
        when(articleRepository.findByPublishDateBetween(pageable, startDate, endDate)).thenReturn(page);
        Page<Article> result = newsService.getAllArticlesPublishedBetween(pageable, startDate, endDate);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllArticlesPublishedAfter() throws Exception {
        Page<Article> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(2L);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);
        when(page.getContent()).
                thenReturn(Arrays.asList(article1,
                        article2));
        Pageable pageable = new PageRequest(0, 10);
        LocalDateTime startDate = LocalDateTime.
                of(2015, Month.AUGUST, 10, 8, 15);
        when(articleRepository.findByPublishDateAfter(pageable, startDate)).thenReturn(page);
        Page<Article> result = newsService.getAllArticlesPublishedAfter(pageable, startDate);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllArticlesPublishedBefore() throws Exception {
        Page<Article> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(2L);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);
        when(page.getContent()).
                thenReturn(Arrays.asList(article1,
                        article2));
        Pageable pageable = new PageRequest(0, 10);
        LocalDateTime endDate = LocalDateTime.now();
        when(articleRepository.findByPublishDateBefore(pageable, endDate)).thenReturn(page);
        Page<Article> result = newsService.getAllArticlesPublishedBefore(pageable, endDate);
        assertEquals(2, result.getContent().size());
    }
}