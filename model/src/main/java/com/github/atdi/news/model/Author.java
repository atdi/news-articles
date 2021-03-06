package com.github.atdi.news.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "authors")
@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private Set<Article> articles;
}
