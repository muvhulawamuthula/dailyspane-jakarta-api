package com.dailyspane.jobs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateJobRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Pay is required")
    @DecimalMin(value = "1.00", message = "Pay must be greater than 0")
    private BigDecimal pay;

    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
