package com.neohoon.excel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "test_entity_detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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


}
