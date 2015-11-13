package com.github.atdi.news.server.resources;

import com.github.atdi.news.model.Author;
import com.github.atdi.news.server.exceptions.NewsException;
import com.github.atdi.news.server.services.AuthorService;
import com.github.atdi.news.server.util.UUIDUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

/**
 * Author REST resource.
 *
 * Created by aurelavramescu on 13/11/15.
 */
@RequiredArgsConstructor(onConstructor = @__({@Inject}))
@Component
@Path("author")
public class AuthorResource {

    private final AuthorService authorService;

    /**
     * Generate the author id.
     *
     * @return id
     */
    @Produces({MediaType.TEXT_PLAIN,
            MediaType.APPLICATION_JSON})
    @POST
    public Response create() {
        String id = authorService.generateId();
        return Response
                .created(URI.create("author/" + id))
                .entity(id).build();
    }

    /**
     * Save author.
     *
     * @param id      author id
     * @param author author
     * @return author
     */
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PUT
    public Response save(@PathParam("id") final String id,
                         @Valid final Author author) {

        UUIDUtils.checkUUID(id);

        if (!id.equals(author.getId())) {
            throw new NewsException("Article not found",
                    Response.Status.NOT_FOUND);
        }

        return Response.ok(authorService.save(author)).build();
    }

    /**
     * Get author.
     *
     * @param id author id
     * @return author
     */
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response getById(@PathParam("id") final String id) {

        UUIDUtils.checkUUID(id);

        Author author = authorService.find(id);
        if (author == null) {
            throw new NewsException("Author not found",
                    Response.Status.NOT_FOUND);
        }

        return Response.ok(author).build();
    }


}
