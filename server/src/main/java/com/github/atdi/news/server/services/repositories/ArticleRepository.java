package com.github.atdi.news.server.services.repositories;

import com.github.atdi.news.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

/**
 * Article data repository.
 * <p>
 * Created by aurelavramescu on 12/11/15.
 */
public interface ArticleRepository extends CrudRepository<Article, String> {

    /**
     * Find all articles.
     *
     * @param pageable pagination details
     * @return articles
     */
    Page<Article> findAll(final Pageable pageable);

    /**
     * Find articles published between specified dates.
     *
     * @param pageable  pagination details
     * @param startDate start date
     * @param endDate   end date
     * @return articles
     */
    Page<Article> findByPublishDateBetween(final Pageable pageable,
                                           final LocalDateTime startDate,
                                           final LocalDateTime endDate);

    /**
     * Find articles published after specified date.
     *
     * @param pageable  pagination details
     * @param startDate start date
     * @return articles
     */
    Page<Article> findByPublishDateAfter(final Pageable pageable,
                                         final LocalDateTime startDate);

    /**
     * Find articles published before specified date.
     *
     * @param pageable pagination details
     * @param endDate  end date
     * @return articles
     */
    Page<Article> findByPublishDateBefore(final Pageable pageable,
                                          final LocalDateTime endDate);
}
