package com.lhcastilho.movie.studio;

import static com.lhcastilho.movie.studio.StudioMapper.mapStudioError;

import java.net.URI;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@RequestScoped
@Path("studios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudioResource {

    @Inject
    private StudioService studioService;

    @POST
    public Response createStudio(StudioData newStudioData) {
        try {
            StudioResponse response = studioService
                .createStudio(newStudioData.getName());

            if (response.getError() != null) {
                return Response
                    .status(Status.BAD_REQUEST)
                    .entity(response)
                    .build();
            }

            URI producerURI = new URI("studios/" + response.getId());
            return Response
                    .created(producerURI)
                    .entity(response)
                    .build();
        } catch(Exception e) {
            StudioResponse response = 
                mapStudioError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }

    @GET
    @Path("{id}")
    public Response findById(@PathParam( "id" ) int id) {
        try {
            StudioResponse response = studioService.readStudio(id);

            if (response == null) {
                return Response
                    .status(Status.NO_CONTENT)
                    .build();
            }
    
            if (response.getError() != null) {
                return Response
                    .status(Status.BAD_REQUEST)
                    .entity(response)
                    .build();
            }

            return Response
                .ok(response)
                .build();
        } catch(Exception e) {
            StudioResponse response = 
                mapStudioError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateById(@PathParam( "id" ) int id, StudioData studioData) {
        try {
            StudioResponse response = studioService
                .updateStudio(id, studioData.getName());

            if (response.getError() != null) {
                return Response
                    .status(Status.BAD_REQUEST)
                    .entity(response)
                    .build();
            }

            return Response
                .ok(response)
                .build();
        } catch(Exception e) {
            StudioResponse response = 
                mapStudioError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteById(@PathParam( "id" ) int id) {
        try {
            StudioResponse response = studioService
                .deleteStudio(id);

            if (response.getError() != null) {
                return Response
                    .status(Status.BAD_REQUEST)
                    .entity(response)
                    .build();
            }

            return Response
                .noContent()
                .build();
        } catch(Exception e) {
            StudioResponse response = 
                mapStudioError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }

}
