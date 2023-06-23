package com.neohoon.excel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_entity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

}
