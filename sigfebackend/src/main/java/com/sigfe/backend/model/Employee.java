package com.sigfe.backend.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table (name = "Employees")
public class Employee {

    @Id
    Long id;

    @Column (length = 50, nullable = false)
    String name;

    @Column (length = 50, nullable = false)
    String jobType;

    @Column (precision = 10, scale = 2, nullable = false)
    BigDecimal salary;

    public Long getId() {
        return id;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
}
