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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Article REST resource.
 * <p>
 * Created by aurelavramescu on 13/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Component
@Path("article")
public class ArticleResource {

    private static final String DATE_PATTERN = "uuuu-MM-dd'T'HH:mm:ss";

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
     * @param page     page
     * @param size     size
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
     * @param page    page
     * @param size    size
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

    /**
     * Get all articles published between specified dates.
     *
     * @param startDate from date
     * @param endDate   to date
     * @param page      page number
     * @param size      page size
     * @return articles
     */
    @Path("published")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getByPublishedDate(
            @QueryParam("startDate") final String startDate,
            @QueryParam("endDate") final String endDate,
            @QueryParam("page") final int page,
            @QueryParam("size") final int size) {
        try {

            if (startDate != null && endDate != null) {
                LocalDateTime sDate = LocalDateTime.parse(startDate,
                        DateTimeFormatter.ofPattern(DATE_PATTERN));
                LocalDateTime eDate = LocalDateTime.parse(endDate,
                        DateTimeFormatter.ofPattern(DATE_PATTERN));
                Page<Article> articles = articleService.
                        getAllPublishedBetween(
                        new PageRequest(page, size),
                        sDate,
                        eDate);
                return Response.ok(articles).build();
            }

            if (startDate != null) {
                LocalDateTime sDate = LocalDateTime.parse(startDate,
                        DateTimeFormatter.ofPattern(DATE_PATTERN));
                Page<Article> articles = articleService.
                        getAllPublishedAfter(
                        new PageRequest(page, size),
                        sDate);
                return Response.ok(articles).build();
            }

            if (endDate != null) {
                LocalDateTime eDate = LocalDateTime.parse(endDate,
                        DateTimeFormatter.ofPattern(DATE_PATTERN));
                Page<Article> articles = articleService.
                        getAllPublishedBefore(
                        new PageRequest(page, size),
                        eDate);
                return Response.ok(articles).build();
            }

            throw new NewsException("You must specify at least one date",
                    Response.Status.BAD_REQUEST);

        } catch (DateTimeParseException e) {
            throw new NewsException("Date pattern is not matching: "
                    + DATE_PATTERN,
                    Response.Status.BAD_REQUEST, e);
        }
    }

}
