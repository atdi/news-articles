package com.github.atdi.news.server.services.repositories;

import com.github.atdi.news.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author data repository.
 *
 * Created by aurelavramescu on 12/11/15.
 */
public interface AuthorJpaRepository extends JpaRepository<Author, String> {
}
