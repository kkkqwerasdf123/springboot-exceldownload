package com.neohoon.excel.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
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
}
