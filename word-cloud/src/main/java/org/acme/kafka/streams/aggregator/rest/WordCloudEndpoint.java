package org.acme.kafka.streams.aggregator.rest;

import org.acme.kafka.streams.aggregator.streams.InteractiveQueries;
import org.acme.kafka.streams.aggregator.streams.PipelineMetadata;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@Path("/api")
public class WordCloudEndpoint {
    @Inject
    InteractiveQueries interactiveQueries;

    /*@ConfigProperty(name = "quarkus.http.ssl-port")
    int sslPort;*/

    @GET
    @Path("/top/{count}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTopWords(@PathParam("count") int count) {
        Map<String, Long> result = interactiveQueries.getAllTimeHigh(count);

        return Response.ok(result).build();
    }

    @GET
    @Path("/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PipelineMetadata> getMetaData() {
        return interactiveQueries.getMetaData();
    }

    /*private URI getOtherUri(String host, int port, int id) {
        try {
            String scheme = (port == sslPort) ? "https" : "http";
            return new URI(scheme + "://" + host + ":" + port + "/weather-stations/data/" + id);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }*/
}
