package com.github.atdi.news.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * News article POJO representation.
 * <p>
 * Created by aurelavramescu on 12/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "articles")
@Entity
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Article {

    @Id
    @NotNull
    private String id;

    @Size(min = 3, max = 255)
    @NotNull
    private String header;

    @Size(min = 3, max = 600)
    @NotNull
    private String description;

    @Size(min = 3, max = 6000)
    @NotNull
    private String text;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull
    private Set<String> keywords;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_article",
            joinColumns = {@JoinColumn(name = "article_id",
                    referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id",
                    referencedColumnName = "id")})
    private Set<Author> authors;

}
