package com.github.thelifesnak3.accenture.resource;

import com.github.thelifesnak3.accenture.dto.CreateWishlistDTO;
import com.github.thelifesnak3.accenture.dto.WishlistDTO;
import com.github.thelifesnak3.accenture.entity.Wishlist;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.service.WishlistService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/wishlist")
@Tag(name = "Wishlist")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")
@SecurityScheme(
    securitySchemeName = "accenture-oauth",
    type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/accenture/protocol/openid-connect/token"))
)
public class WishlistResource {

    @Inject WishlistService wishlistService;

    @Inject Logger log;

    @GET
    @Operation(
            summary = "Perform the search for wishlist.",
            description = "Service responsible for searching all the wishlists.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = Wishlist.class))
            ),
            @APIResponse(
                    responseCode = "204",
                    description = "No data found."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response getAll(@QueryParam("gender") String gender,
                           @QueryParam("actor") String actor,
                           @QueryParam("director") String director) {
        log.info("Request getById wishlist - GET /wishlist");

        List<Wishlist> list = wishlistService.getAll(gender, actor, director);

        return Response
            .status(list.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
            .entity(list)
            .build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Perform the search for wishlist by id.",
            description = "Service responsible for searching the wishlist by id from url path parameter")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = WishlistDTO.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "The given id is invalid."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Could not find the informed wishlist."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public WishlistDTO getById(
        @PathParam("id") String id
    ) {
        log.info("Request getById wishlist - GET /wishlist/"+id);

        return wishlistService.getById(id);
    }

    @POST
    @Operation(
            summary = "Perform wishlist registration.",
            description = "Service responsible for registering wishlist.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "201",
                    description = "Successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Some of the required fields were not informed."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response create(CreateWishlistDTO createWishlistDTO) throws ValidateException {
        log.info("Request create wishlist - POST /wishlist");
        log.info("Request body:");
        log.info(createWishlistDTO);

        wishlistService.create(createWishlistDTO);

        return Response.status(Response.Status.CREATED).build();
    }
}