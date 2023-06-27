package com.neohoon.excel.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_entity")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_entity_id")
    private Long id;

    private String testString;
    private Long testLong;
    private Integer testInteger;

    private LocalDateTime testDatetime;
    private LocalDate testDate;
    private LocalTime testTime;

    private Double testDouble;
    private Float testFloat;

    private String testImageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "testEntity")
    private List<TestEntityDetail> details = new ArrayList<>();

    protected TestEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getTestString() {
        return testString;
    }

    public Long getTestLong() {
        return testLong;
    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public LocalDateTime getTestDatetime() {
        return testDatetime;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public LocalTime getTestTime() {
        return testTime;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public Float getTestFloat() {
        return testFloat;
    }

    public String getTestImageUrl() {
        return testImageUrl;
    }

    public List<TestEntityDetail> getDetails() {
        return details;
    }
}
