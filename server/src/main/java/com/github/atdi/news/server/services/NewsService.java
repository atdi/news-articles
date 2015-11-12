package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.services.repositories.ArticleRepository;
import com.github.atdi.news.server.services.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Created by aurelavramescu on 12/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Service
public class NewsService {

    private final ArticleRepository articleRepository;

    private final AuthorRepository authorRepository;

    /**
     * Generate a new id.
     *
     * @return id
     */
    public final String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Save an article.
     *
     * @param article article to be saved
     * @return article
     */
    public final Article saveArticle(final Article article) {
        return articleRepository.save(article);
    }

    /**
     * Find article by id.
     *
     * @param id article id
     * @return article article
     */
    public final Article findArticle(final String id) {
        return articleRepository.findOne(id);
    }

    /**
     * Delete specified article.
     *
     * @param id article id
     */
    public final void deleteArticle(final String id) {
        articleRepository.delete(id);
    }

    /**
     * Get all articles.
     *
     * @param pageable pagination settings
     * @return articles
     */
    public final Page<Article> getAllArticles(final Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    /**
     * Find all articles published between specified dates.
     *
     * @param pageable pagination details
     * @param startDate start date
     * @param endDate end date
     * @return articles
     */
    public final Page<Article> getAllArticlesPublishedBetween(
            final Pageable pageable,
            final LocalDateTime startDate,
            final LocalDateTime endDate) {
        return articleRepository.findByPublishDateBetween(pageable,
                startDate,
                endDate);
    }

    /**
     * Find all articles published after specified date.
     *
     * @param pageable pagination details
     * @param startDate start date
     * @return articles
     */
    public final Page<Article> getAllArticlesPublishedAfter(
            final Pageable pageable,
            final LocalDateTime startDate) {
        return articleRepository.findByPublishDateAfter(pageable, startDate);
    }

    /**
     * Find all articles published before specified date.
     *
     * @param pageable pagination details
     * @param endDate end date
     * @return articles
     */
    public final Page<Article> getAllArticlesPublishedBefore(
            final Pageable pageable,
            final LocalDateTime endDate) {
        return articleRepository.findByPublishDateBefore(pageable, endDate);
    }

}
