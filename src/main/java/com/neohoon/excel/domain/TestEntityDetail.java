package com.neohoon.excel.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "test_entity_detail")
public class TestEntityDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_entity_detail_id")
    private Long id;

    private Integer detailNo;
    private String detailName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_entity_id")
    private TestEntity testEntity;

    protected TestEntityDetail() {
    }

    public Long getId() {
        return id;
    }

    public Integer getDetailNo() {
        return detailNo;
    }

    public String getDetailName() {
        return detailName;
    }

    public TestEntity getTestEntity() {
        return testEntity;
    }
}
