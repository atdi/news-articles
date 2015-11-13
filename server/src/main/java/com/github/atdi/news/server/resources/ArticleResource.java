package com.github.atdi.news.server.resources;

import com.github.atdi.news.model.Article;
import com.github.atdi.news.server.exceptions.NewsException;
import com.github.atdi.news.server.services.ArticleService;
import com.github.atdi.news.server.util.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * Article REST resource.
 * <p>
 * Created by aurelavramescu on 13/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Component
@Path("article")
public class ArticleResource {

    private final ArticleService articleService;


    /**
     * Generate the article id.
     *
     * @return id
     */
    @Produces({MediaType.TEXT_PLAIN,
            MediaType.APPLICATION_JSON})
    @POST
    public Response create() {
        String id = articleService.generateId();
        return Response
                .created(URI.create("article/" + id))
                .entity(id).build();
    }

    /**
     * Save article.
     *
     * @param id      article id
     * @param article article
     * @return article
     */
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response save(@PathParam("id") final String id,
                         @Valid final Article article) {

        UUIDUtils.checkUUID(id);

        if (!id.equals(article.getId())) {
            throw new NewsException("Article not found",
                    Response.Status.NOT_FOUND);
        }
        Article toBeSaved = article
                .toBuilder()
                .publishDate(LocalDateTime.now()).build();

        return Response.ok(articleService.save(toBeSaved)).build();
    }


    /**
     * Get article.
     *
     * @param id article id
     * @return article
     */
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getById(@PathParam("id") final String id) {

        UUIDUtils.checkUUID(id);

        Article article = articleService.find(id);
        if (article == null) {
            throw new NewsException("Article not found",
                    Response.Status.NOT_FOUND);
        }

        return Response.ok(article).build();
    }

    /**
     * Get all articles by author.
     *
     * @param authorId author id
     * @param page page
     * @param size size
     * @return articles
     */
    @Path("author/{authorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getByAuthorId(
            @PathParam("authorId") final String authorId,
            @QueryParam("page") final int page,
            @QueryParam("size") final int size) {

        UUIDUtils.checkUUID(authorId);

        Page<Article> articles = articleService.getAllByAuthor(
                new PageRequest(page, size),
                authorId);

        return Response.ok(articles).build();
    }


    /**
     * Get all articles by keyword.
     *
     * @param keyword keyword
     * @param page page
     * @param size size
     * @return articles
     */
    @Path("keyword")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getByKeyword(
            @NotNull
            @Size(min = 3)
            @QueryParam("keyword")
            final String keyword,
            @QueryParam("page") final int page,
            @QueryParam("size") final int size) {

        Page<Article> articles = articleService.getAllByKeyword(
                new PageRequest(page, size),
                keyword);

        return Response.ok(articles).build();
    }

}
