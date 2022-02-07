package com.github.thelifesnak3.accenture.service.omdb;

import com.github.thelifesnak3.accenture.dto.MovieDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@ApplicationScoped
@RegisterRestClient(configKey = "omdb")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterProvider(OmdbResponseMapper.class)
//@RegisterClientHeaders(OmdbHeaderFactory.class)
public interface OmdbService {

    @GET
    MovieDTO getMovieById(@QueryParam("i") String id);
}
