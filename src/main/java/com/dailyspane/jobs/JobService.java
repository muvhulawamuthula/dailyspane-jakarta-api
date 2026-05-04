package com.dailyspane.jobs;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@ApplicationScoped
public class JobService {

    private final List<JobPost> jobs = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public List<JobPost> findAll() {
        return jobs;
    }

    public JobPost create(CreateJobRequest request) {
        JobPost job = new JobPost();
        job.setId(idGenerator.incrementAndGet());
        job.setTitle(request.getTitle());
        job.setLocation(request.getLocation());
        job.setPay(request.getPay());
        job.setDescription(request.getDescription());

        jobs.add(job);
        return job;
    }
}