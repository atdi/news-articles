package com.github.atdi.news.server.services;

import com.github.atdi.news.model.Author;
import com.github.atdi.news.server.services.repositories.AuthorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.UUID;

/**
 * Author service.
 * <p>
 * Created by aurelavramescu on 13/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Service
public class AuthorService {

    private final AuthorJpaRepository authorJpaRepository;

    /**
     * Generate a new id.
     *
     * @return id
     */
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Save an author.
     *
     * @param author author
     * @return saved author
     */
    public Author save(final Author author) {
        return authorJpaRepository.save(author);
    }

    /**
     * Find author by id.
     *
     * @param id author id
     * @return author
     */
    public Author find(final String id) {
        return authorJpaRepository.findOne(id);
    }
}
