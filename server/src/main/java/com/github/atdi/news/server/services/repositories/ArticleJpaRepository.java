package com.github.atdi.news.server.services.repositories;

import com.github.atdi.news.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

/**
 * Article data repository.
 *
 * Created by aurelavramescu on 12/11/15.
 */
public interface ArticleJpaRepository extends JpaRepository<Article, String> {


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

    /**
     * Find articles by given author.
     *
     * @param pageable pagination details
     * @param authorId author id
     * @return articles
     */
    @Query("select a from Article a join a.authors au where au.id = ?1")
    Page<Article> findByAuthor(final Pageable pageable,
                               final String authorId);

    /**
     * Find articles by given keyword.
     *
     * @param pageable pagination details
     * @param keyword keyword
     * @return articles
     */
    @Query("select a from Article a join a.keywords k where k = ?1")
    Page<Article> findByKeyword(final Pageable pageable,
                                final String keyword);
}
