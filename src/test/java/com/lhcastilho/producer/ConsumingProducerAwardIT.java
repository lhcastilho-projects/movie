package com.lhcastilho.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.lhcastilho.movie.producer.ProducerIntervalResponse;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ConsumingProducerAwardIT {

    private static final String BASE_URL = "http://localhost:9080/movie/api/producers/awards";

    private Client client;
    private Response response;

    @BeforeEach
    public void setup() {
      client = ClientBuilder.newClient();
    }

    @Test
    public void testMinMaxWorstAward() {

        // Make HTTP Get request
        response = client.target(BASE_URL).request().get();

        assertEquals(200, response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON.toString(), response.getMediaType().toString());

        String strJsonResponse = response.readEntity(String.class);
        assertNotNull(strJsonResponse);


        Jsonb jsonb = JsonbBuilder.create();
        ProducerIntervalResponse jsonResponse = jsonb
            .fromJson(strJsonResponse, ProducerIntervalResponse.class);

        // Validate response size of min and max lists
        assertEquals(jsonResponse.getMin().size(), 1);
        assertEquals(jsonResponse.getMax().size(), 1);

        // Validate min producer result
        String minProducerName = jsonResponse.getMin().get(0).getProducer();
        assertEquals("Joel Silver", minProducerName);
        int minInterval = jsonResponse.getMin().get(0).getInterval();
        assertEquals(1, minInterval);
        int minPreviousWinYear = jsonResponse.getMin().get(0).getPreviousWin();
        assertEquals(1990, minPreviousWinYear);
        int minFollowingWinYear = jsonResponse.getMin().get(0).getFollowingWin();
        assertEquals(1991, minFollowingWinYear);

        // Validate max producer result
        String maxProducerName = jsonResponse.getMax().get(0).getProducer();
        assertEquals("Matthew Vaughn", maxProducerName);
        int maxInterval = jsonResponse.getMax().get(0).getInterval();
        assertEquals(13, maxInterval);
        int previousWinYear = jsonResponse.getMax().get(0).getPreviousWin();
        assertEquals(2002, previousWinYear);
        int followingWinYear = jsonResponse.getMax().get(0).getFollowingWin();
        assertEquals(2015, followingWinYear);
    }
}
