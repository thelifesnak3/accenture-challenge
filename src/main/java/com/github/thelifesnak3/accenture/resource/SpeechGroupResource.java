package com.github.thelifesnak3.accenture.resource;

import com.github.thelifesnak3.accenture.dto.CreateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.dto.UpdateSpeechGroupDTO;
import com.github.thelifesnak3.accenture.entity.SpeechGroup;
import com.github.thelifesnak3.accenture.exception.validate.ValidateException;
import com.github.thelifesnak3.accenture.service.SpeechGroupService;
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

@Path("/speech-group")
@Tag(name = "Speech Group")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("proprietario")
@SecurityScheme(
        securitySchemeName = "accenture-oauth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/accenture/protocol/openid-connect/token"))
)
public class SpeechGroupResource {

    @Inject SpeechGroupService speechGroupService;
    @Inject Logger log;

    @GET
    @Operation(
            summary = "Perform the search for speech group.",
            description = "Service responsible for searching all the speech groups.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(
                                    type = SchemaType.ARRAY,
                                    implementation = SpeechGroup.class))
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
        log.info("Request getAll speech-group - GET /speech-group");
        List<SpeechGroup> list = speechGroupService.getAll();

        return Response
            .status(list.isEmpty() ? Response.Status.NO_CONTENT : Response.Status.OK)
            .entity(list)
            .build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Perform the search for speech group by id.",
            description = "Service responsible for searching the speech group by id from url path parameter")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "200",
                    description = "Success.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(
                                    implementation = SpeechGroup.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "The given id is invalid."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Could not find the informed speech group."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public SpeechGroup getById(@PathParam("id") String id) {
        log.info("Request getById speech-group - GET /speech-group/"+id);

        return speechGroupService.getById(id);
    }

    @POST
    @Operation(
            summary = "Perform speech group registration.",
            description = "Service responsible for registering speech group.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "201",
                    description = "Successfully created."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "1 - Some of the required fields were not informed.</br>" +
                            "2 - Its mandatory to inform users in case of private Speech Group."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public Response create(CreateSpeechGroupDTO createSpeechGroupDTO) throws ValidateException {
        log.info("Request create speech group - POST /speech-group");
        log.info("Request body:");
        log.info(createSpeechGroupDTO);

        speechGroupService.create(createSpeechGroupDTO);

        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Operation(
            summary = "Perform speech group update.",
            description = "Service responsible for updating speech group.")
    @APIResponses(value = {
            @APIResponse(
                    responseCode = "204",
                    description = "Successfully updated."
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "1 - Some of the required fields were not informed.</br>" +
                            "2 - The given id is invalid."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Could not find the informed speech group."
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error."
            )
    })
    public void update(@PathParam("id") String id, UpdateSpeechGroupDTO updateSpeechGroupDTO) throws ValidateException {
        log.info("Request update speech group - PUT /speech-group/"+id);
        log.info("Request body:");
        log.info(updateSpeechGroupDTO);

        speechGroupService.update(updateSpeechGroupDTO, id);
    }

    @DELETE
    @Path("{id}")
    @Operation(
            summary = "Perform speech group registration.",
            description = "Service responsible for registering speech group.")
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
        log.info("Request delete speech group - DELETE /speech-group/"+id);

        speechGroupService.delete(id);
    }
}
