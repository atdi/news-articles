package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.services.repositories.ArticleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * Jpa implementation for {@link ArticleSearchService}.
 *
 * Created by aurelavramescu on 13/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ArticleJpaSearch implements ArticleSearchService {

    private final ArticleJpaRepository articleJpaRepository;


    @Override
    public Page<Article> searchPublishedBetween(
            final Pageable pageable,
            final LocalDateTime startDate,
            final LocalDateTime endDate) {
        return articleJpaRepository.findByPublishDateBetween(pageable,
                startDate,
                endDate);
    }


    @Override
    public Page<Article> searchPublishedAfter(
            final Pageable pageable,
            final LocalDateTime startDate) {
        return articleJpaRepository.findByPublishDateAfter(pageable, startDate);
    }


    @Override
    public Page<Article> searchPublishedBefore(
            final Pageable pageable,
            final LocalDateTime endDate) {
        return articleJpaRepository.findByPublishDateBefore(pageable, endDate);
    }

    @Override
    public Page<Article> searchByKeyword(final String keyword) {
        return null;
    }
}
