package com.github.atdi.news.server.services.repositories;

import com.github.atdi.news.model.Author;
import org.springframework.data.repository.CrudRepository;

/**
 * Author data repository.
 *
 * Created by aurelavramescu on 12/11/15.
 */
public interface AuthorRepository extends CrudRepository<Author, String> {
}
