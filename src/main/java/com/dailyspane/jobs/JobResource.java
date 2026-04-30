package com.dailyspane.jobs;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/jobs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JobResource {

    @Inject
    JobService jobService;

    @GET
    public List<JobPost> getAllJobs() {
        return jobService.findAll();
    }

    @POST
    public Response createJob(JobPost job) {
        JobPost created = jobService.create(job);
        return Response.status(Response.Status.CREATED).entity(created).build();
    }
}
