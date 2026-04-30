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

    public JobPost create(JobPost job) {
        job.setId(idGenerator.incrementAndGet());
        jobs.add(job);
        return job;
    }
}