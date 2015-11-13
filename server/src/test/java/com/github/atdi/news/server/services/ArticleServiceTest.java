package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.services.repositories.ArticleJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ArticleServiceTest {

    @Mock
    private ArticleJpaRepository articleJpaRepository;

    private ArticleService articleService;

    @Before
    public void setUp() {
        ArticleSearchService searchService = new ArticleJpaSearch(articleJpaRepository);
        articleService = new ArticleService(articleJpaRepository,
                searchService);
    }

    @Test
    public void testGenerateId() throws Exception {
        String id = articleService.generateId();
        assertNotNull(id);
    }

    @Test
    public void testSave() throws Exception {
        Article article = Article.builder()
                .id(articleService.generateId())
                .header("This is a test")
                .description("This is a small article")
                .keywords(Sets.<String>newSet("fire", "water"))
                .text("Content")
                .build();
        when(articleJpaRepository.save(article)).thenReturn(article);
        Article savedInstance = articleService.save(article);
        assertEquals(article, savedInstance);
    }

    @Test
    public void testDelete() throws Exception {
        String id = articleService.generateId();
        Article article = Article.builder()
                .id(id)
                .header("This is a test")
                .description("This is a small article")
                .keywords(Sets.<String>newSet("fire", "water"))
                .text("Content")
                .build();
        when(articleJpaRepository.save(article)).thenReturn(article);
        articleService.save(article);
        articleService.delete(id);
        Article deletedArticle = articleService.find(id);
        assertNull(deletedArticle);
    }

    @Test
    public void testGetAll() throws Exception {
        Page<Article> page = mock(Page.class);
        when(page.getTotalPages()).thenReturn(1);
        when(page.getTotalElements()).thenReturn(2L);
        Article article1 = mock(Article.class);
        Article article2 = mock(Article.class);
        when(page.getContent()).
                thenReturn(Arrays.asList(article1,
                        article2));
        Pageable pageable = new PageRequest(0, 10);
        when(articleJpaRepository.findAll(pageable)).thenReturn(page);
        Page<Article> result = articleService.getAll(pageable);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllPublishedBetween() throws Exception {
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
        when(articleJpaRepository.findByPublishDateBetween(pageable, startDate, endDate)).thenReturn(page);
        Page<Article> result = articleService.getAllPublishedBetween(pageable, startDate, endDate);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllPublishedAfter() throws Exception {
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
        when(articleJpaRepository.findByPublishDateAfter(pageable, startDate)).thenReturn(page);
        Page<Article> result = articleService.getAllPublishedAfter(pageable, startDate);
        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testGetAllPublishedBefore() throws Exception {
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
        when(articleJpaRepository.findByPublishDateBefore(pageable, endDate)).thenReturn(page);
        Page<Article> result = articleService.getAllPublishedBefore(pageable, endDate);
        assertEquals(2, result.getContent().size());
    }
}