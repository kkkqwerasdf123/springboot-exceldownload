package com.neohoon.excel.repository;

import com.neohoon.excel.domain.TestEntity;
import com.neohoon.excel.dto.TestDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    @Query("select new com.neohoon.excel.dto.TestDto(t.id, t.testString, t.testLong, t.testInteger, t.testDatetime, t.testDate, t.testTime, t.testDouble, t.testFloat) " +
            "from TestEntity t order by t.id desc")
    Stream<TestDto> findAllDto();

    @Query("select t from TestEntity t left join fetch TestEntityDetail td where t.id > 0 ")
    Stream<TestEntity> findAllEntity();

}
