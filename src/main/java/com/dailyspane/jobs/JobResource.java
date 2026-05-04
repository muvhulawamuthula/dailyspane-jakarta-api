package com.dailyspane.jobs;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
    public List<JobResponse> getAllJobs() {
        return jobService.findAll()
                .stream()
                .map(JobResponse::from)
                .toList();
    }

    @POST
    public Response createJob(@Valid CreateJobRequest request) {
        JobPost created = jobService.create(request);

        return Response.status(Response.Status.CREATED)
                .entity(JobResponse.from(created))
                .build();
    }
}
