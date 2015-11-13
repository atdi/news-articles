package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Interface for search articles.
 *
 * Created by aurelavramescu on 13/11/15.
 */
public interface ArticleSearchService {

    /**
     * Find all articles published between specified dates.
     *
     * @param pageable pagination details
     * @param startDate start date
     * @param endDate end date
     * @return articles
     */
    Page<Article> searchPublishedBetween(
            Pageable pageable,
            LocalDateTime startDate,
            LocalDateTime endDate);

    /**
     * Find all articles published after specified date.
     *
     * @param pageable pagination details
     * @param startDate start date
     * @return articles
     */
    Page<Article> searchPublishedAfter(
            Pageable pageable,
            LocalDateTime startDate);

    /**
     * Find all articles published before specified date.
     *
     * @param pageable pagination details
     * @param endDate end date
     * @return articles
     */
    Page<Article> searchPublishedBefore(
            Pageable pageable,
            LocalDateTime endDate);

    /**
     * Find all articles for specified keyword.
     *
     * @param keyword keyword
     * @return articles
     */
    Page<Article> searchByKeyword(final String keyword);


}
