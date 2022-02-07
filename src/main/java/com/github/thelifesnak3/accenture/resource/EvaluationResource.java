package com.github.thelifesnak3.accenture.resource;

import com.github.thelifesnak3.accenture.dto.CreateEvaluationDTO;
import com.github.thelifesnak3.accenture.dto.UpdateEvaluationDTO;
import com.github.thelifesnak3.accenture.dto.WishlistDTO;
import com.github.thelifesnak3.accenture.entity.Evaluation;
import com.github.thelifesnak3.accenture.entity.Wishlist;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.service.EvaluationService;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/evaluation")
@Tag(name = "Evaluation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")
@SecurityScheme(
    securitySchemeName = "accenture-oauth",
    type = SecuritySchemeType.OAUTH2,
    flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/accenture/protocol/openid-connect/token"))
)
public class EvaluationResource {

    @Inject EvaluationService evaluationService;

    @Inject Logger log;

    @GET
    @Operation(
            summary = "Perform the search for evaluation.",
            description = "Service responsible for searching all the evaluations.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = Evaluation.class))
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
    public Response getAll() {
        log.info("Request getAll evaluation - GET /evaluation");

        List<Evaluation> list = evaluationService.getAll();

        return Response
            .status(list.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
            .entity(list)
            .build();
    }

    @GET
    @Path("/movie/{idMovie}")
    @Operation(
            summary = "Perform the search for evaluation by movie id.",
            description = "Service responsible for searching the evaluation by movie id from url path parameter")
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
                    description = "Could not find any evaluation for the given movie id."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Evaluation getByMovie(@PathParam("idMovie") String idMovie) {
        log.info("Request getByMovie evaluation - GET /evaluation/movie/"+idMovie);

        return evaluationService.getByMovie(idMovie);
    }

    @POST
    @Operation(
            summary = "Perform evaluation registration.",
            description = "Service responsible for registering evaluation.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "201",
                    description = "Successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "1 - Some of the required fields were not informed.</br>" +
                            "2 - There is already an evaluation for the informed movie.</br>" +
                            "3 - Could not find any movie with the given id: ."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response create(CreateEvaluationDTO createEvaluationDTO) throws ValidateException {
        log.info("Request create evaluation - POST /evaluation");
        log.info("Request body:");
        log.info(createEvaluationDTO);

        evaluationService.create(createEvaluationDTO);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Operation(
            summary = "Perform evaluation update.",
            description = "Service responsible for updating evaluation.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "204",
                    description = "Successfully updated."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "1 - Some of the required fields were not informed.</br>" +
                            "2 - The given id is invalid.</br>" +
                            "3 - Could not find any movie with the given id: ."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Could not find the informed evaluation."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public void update(@PathParam("id") String id, UpdateEvaluationDTO updateEvaluationDTO) throws ValidateException {
        log.info("Request update evaluation - PUT /evaluation/"+id);
        log.info("Request body:");
        log.info(updateEvaluationDTO);

        evaluationService.update(updateEvaluationDTO, id);
    }

    @DELETE
    @Path("{id}")
    @Operation(
            summary = "Perform evaluation registration.",
            description = "Service responsible for registering evaluation.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "204",
                    description = "Successfully deleted."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "The given id is invalid."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Could not find the informed evaluation."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public void delete(@PathParam("id") String id) {
        log.info("Request delete evaluation - DELETE /evaluation/"+id);

        evaluationService.delete(id);
    }
}
