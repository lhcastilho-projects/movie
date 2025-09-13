package com.lhcastilho.movie.producer;

import static com.lhcastilho.movie.producer.ProducerMapper.mapProducerError;

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
@Path("producers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProducerResource {

    @Inject
    private ProducerService producerService;

    @POST
    public Response createProducer(ProducerData newProducerData) {
        try {
            ProducerResponse response = producerService
                .createProducer(newProducerData.getName());

            if (response.getError() != null) {
                return Response
                    .status(Status.BAD_REQUEST)
                    .entity(response)
                    .build();
            }

            URI producerURI = new URI("producers/" + response.getId());
            return Response
                    .created(producerURI)
                    .entity(response)
                    .build();
        } catch(Exception e) {
            ProducerResponse response = 
                mapProducerError(e.getMessage());
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
            ProducerResponse response = producerService.readProducer(id);

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
            ProducerResponse response = 
                mapProducerError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }

    @GET
    @Path("awards")
    public Response findAll() {
        try {
            ProducerIntervalResponse response = producerService
                .awardsInterval(0, 100000);

            if (response == null) {
                return Response
                    .status(Status.NO_CONTENT)
                    .build();
            }

            return Response
                .ok(response)
                .build();
        } catch(Exception e) {
            ProducerResponse response = 
                mapProducerError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }

    @PUT
    @Path("{id}")
    public Response updateById(@PathParam( "id" ) int id, ProducerData producerData) {
        try {
            ProducerResponse response = producerService
                .updateProducer(id, producerData.getName());

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
            ProducerResponse response = 
                mapProducerError(e.getMessage());
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
            ProducerResponse response = producerService
                .deleteProducer(id);

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
            ProducerResponse response = 
                mapProducerError(e.getMessage());
            return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
        }
    }
}
