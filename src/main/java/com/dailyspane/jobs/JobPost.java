package com.dailyspane.jobs;

import java.math.BigDecimal;

public class JobPost {

    private Long id;
    private String title;
    private String location;
    private BigDecimal pay;
    private String description;

    public JobPost() {}

    public JobPost(Long id, String title, String location, BigDecimal pay, String description) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.pay = pay;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public BigDecimal getPay() { return pay; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setPay(BigDecimal pay) { this.pay = pay; }
    public void setDescription(String description) { this.description = description; }
}
