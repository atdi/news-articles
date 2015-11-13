package com.github.atdi.news.server.config;

import com.github.atdi.news.server.services.ArticleJpaSearch;
import com.github.atdi.news.server.services.ArticleSearchService;
import com.github.atdi.news.server.services.repositories.ArticleJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

/**
 * Spring config for jpa search.
 *
 * Created by aurelavramescu on 13/11/15.
 */
@Configuration
public class JpaSearchConfig {

    @Inject
    private ArticleJpaRepository articleJpaRepository;

    /**
     * Create article jpa search service.
     *
     * @return {@link ArticleJpaSearch}
     */
    @Bean
    public ArticleSearchService articleSearchService() {
        return new ArticleJpaSearch(articleJpaRepository);
    }
}
