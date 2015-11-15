package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.services.repositories.ArticleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Article service.
 * <p>
 * Created by aurelavramescu on 12/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Service
public class ArticleService {

    private final ArticleJpaRepository articleJpaRepository;

    private final ArticleSearchService articleSearchService;

    /**
     * Generate a new id.
     *
     * @return id
     */
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Save an article.
     *
     * @param article article to be saved
     * @return article
     */
    public Article save(final Article article) {
        return articleJpaRepository.save(article);
    }

    /**
     * Find article by id.
     *
     * @param id article id
     * @return article article
     */
    public Article find(final String id) {
        return articleJpaRepository.findOne(id);
    }

    /**
     * Delete specified article.
     *
     * @param id article id
     */
    public void delete(final String id) {
        articleJpaRepository.delete(id);
    }

    /**
     * Get all articles.
     *
     * @param pageable pagination settings
     * @return articles
     */
    public Page<Article> getAll(final Pageable pageable) {
        return articleJpaRepository.findAll(pageable);
    }

    /**
     * Find all articles published between specified dates.
     *
     * @param pageable  pagination details
     * @param startDate start date
     * @param endDate   end date
     * @return articles
     */
    public Page<Article> getAllPublishedBetween(
            final Pageable pageable,
            final LocalDateTime startDate,
            final LocalDateTime endDate) {
        return articleSearchService.searchPublishedBetween(pageable,
                startDate,
                endDate);
    }

    /**
     * Find all articles published after specified date.
     *
     * @param pageable  pagination details
     * @param startDate start date
     * @return articles
     */
    public Page<Article> getAllPublishedAfter(
            final Pageable pageable,
            final LocalDateTime startDate) {
        return articleSearchService.
                searchPublishedAfter(pageable, startDate);
    }

    /**
     * Find all articles published before specified date.
     *
     * @param pageable pagination details
     * @param endDate  end date
     * @return articles
     */
    public Page<Article> getAllPublishedBefore(
            final Pageable pageable,
            final LocalDateTime endDate) {
        return articleSearchService.
                searchPublishedBefore(pageable, endDate);
    }

    /**
     * Get all articles by specified keyword.
     *
     * @param pageable pagination details
     * @param keyword keyword
     * @return articles
     */
    public Page<Article> getAllByKeyword(final Pageable pageable,
                                         final String keyword) {
        return articleSearchService.searchByKeyword(pageable, keyword);
    }

    /**
     * Get all articles by specified author.
     *
     * @param pageable pagination details
     * @param authorId author id
     * @return articles
     */
    public Page<Article> getAllByAuthor(final Pageable pageable,
                                         final String authorId) {
        return articleSearchService.searchByAuthor(pageable, authorId);
    }

}
