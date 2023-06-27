package com.neohoon.excel.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestDto {
    private Long id;

    private String testString;
    private Long testLong;
    private Integer testInteger;

    private LocalDateTime testDatetime;
    private LocalDate testDate;
    private LocalTime testTime;

    private Double testDouble;
    private Float testFloat;


    public TestDto(Long id, String testString, Long testLong, Integer testInteger, LocalDateTime testDatetime, LocalDate testDate, LocalTime testTime, Double testDouble, Float testFloat) {
        this.id = id;
        this.testString = testString;
        this.testLong = testLong;
        this.testInteger = testInteger;
        this.testDatetime = testDatetime;
        this.testDate = testDate;
        this.testTime = testTime;
        this.testDouble = testDouble;
        this.testFloat = testFloat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public Long getTestLong() {
        return testLong;
    }

    public void setTestLong(Long testLong) {
        this.testLong = testLong;
    }

    public Integer getTestInteger() {
        return testInteger;
    }

    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }

    public LocalDateTime getTestDatetime() {
        return testDatetime;
    }

    public void setTestDatetime(LocalDateTime testDatetime) {
        this.testDatetime = testDatetime;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public LocalTime getTestTime() {
        return testTime;
    }

    public void setTestTime(LocalTime testTime) {
        this.testTime = testTime;
    }

    public Double getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(Double testDouble) {
        this.testDouble = testDouble;
    }

    public Float getTestFloat() {
        return testFloat;
    }

    public void setTestFloat(Float testFloat) {
        this.testFloat = testFloat;
    }
}
