package com.github.atdi.news.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Author POJO representation.
 * <p>
 * Created by aurelavramescu on 12/11/15.
 */
@Table(name = "authors")
@Entity
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(of = { "id" })
public class Author {

    @Id
    @NotNull
    private String id;

    @Column(name = "first_name")
    @Size(min = 3, max = 100)
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 3, max = 100)
    @NotNull
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private Set<Article> articles;
}
