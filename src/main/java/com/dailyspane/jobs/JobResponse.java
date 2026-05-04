package com.dailyspane.jobs;

import java.math.BigDecimal;

public class JobResponse {

    private Long id;
    private String title;
    private String location;
    private BigDecimal pay;
    private String description;

    public JobResponse() {}

    public JobResponse(Long id, String title, String location, BigDecimal pay, String description) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.pay = pay;
        this.description = description;
    }

    public static JobResponse from(JobPost job) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getLocation(),
                job.getPay(),
                job.getDescription()
        );
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public String getDescription() {
        return description;
    }
}
