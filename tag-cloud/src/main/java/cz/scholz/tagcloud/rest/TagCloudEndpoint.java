package cz.scholz.tagcloud.rest;

import cz.scholz.tagcloud.streams.PipelineMetadata;
import cz.scholz.tagcloud.streams.InteractiveQueries;

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
public class TagCloudEndpoint {
    @Inject
    InteractiveQueries interactiveQueries;

    @GET
    @Path("/all-time/top/{count}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTimeTopWords(@PathParam("count") int count) {
        Map<String, Long> result = interactiveQueries.getAllTimeHighest(count);

        return Response.ok(result).build();
    }

    @GET
    @Path("/all-time/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PipelineMetadata> getAllTimeMetaData() {
        return interactiveQueries.getAllTimeMetaData();
    }

    @GET
    @Path("/latest/top/{count}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestTopWords(@PathParam("count") int count) {
        Map<String, Long> result = interactiveQueries.getLatestHighest(count);

        return Response.ok(result).build();
    }

    @GET
    @Path("/latest/meta-data")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PipelineMetadata> getMetaData() {
        return interactiveQueries.getLatestMetaData();
    }
}
