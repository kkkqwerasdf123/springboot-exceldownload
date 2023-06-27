package com.neohoon.excel.service;

import com.neohoon.excel.domain.TestEntity;
import com.neohoon.excel.dto.TestDto;
import com.neohoon.excel.repository.TestRepository;
import com.neohoon.excel.util.ExcelHandler;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neohoon.excel.util.ExcelDataType.*;

@Service
public class ExcelService {

    private final TestRepository repository;
    private final EntityManager entityManager;

    public ExcelService(TestRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public ExcelHandler<TestDto> byDto() {
        ExcelHandler<TestDto> handler = new ExcelHandler<>("test - dto", "subtitle", TestDto.class);
        return handler.columns(
                handler.column("no", (rowData, cursor) -> cursor.getCurrentTotal()).type(INTEGER),
                handler.column("string", (rowData, cursor) -> rowData.getTestString()).type(STRING),
                handler.column("long", (rowData, cursor) -> rowData.getTestLong()).type(LONG),
                handler.column("integer", (rowData, cursor) -> rowData.getTestInteger()).type(INTEGER),
                handler.column("double", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE),
                handler.column("float", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT),
                handler.column("double_percent", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE_PERCENT),
                handler.column("float_percent", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT_PERCENT),
                handler.column("datetime", (rowData, cursor) -> rowData.getTestDatetime()).type(DATETIME),
                handler.column("date", (rowData, cursor) -> rowData.getTestDate()).type(DATE),
                handler.column("time", (rowData, cursor) -> rowData.getTestTime()).type(TIME),
                handler.column("custom", (rowData, cursor) -> "custom_test_" + rowData.getTestString() + "_" + rowData.getTestLong())
        ).write(repository.findAllDto());
    }

    @Transactional(readOnly = true)
    public ExcelHandler<TestEntity> byEntity() {
        ExcelHandler<TestEntity> handler = new ExcelHandler<>("test entity", "subtitle", TestEntity.class);
        return handler.columns(
                handler.column("no", (rowData, cursor) -> cursor.getCurrentTotal()).type(INTEGER),
                handler.column("string", (rowData, cursor) -> rowData.getTestString()).type(STRING),
                handler.column("long", (rowData, cursor) -> rowData.getTestLong()).type(LONG),
                handler.column("integer", (rowData, cursor) -> rowData.getTestInteger()).type(INTEGER),
                handler.column("double", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE),
                handler.column("float", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT),
                handler.column("double_percent", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE_PERCENT),
                handler.column("float_percent", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT_PERCENT),
                handler.column("datetime", (rowData, cursor) -> rowData.getTestDatetime()).type(DATETIME),
                handler.column("date", (rowData, cursor) -> rowData.getTestDate()).type(DATE),
                handler.column("time", (rowData, cursor) -> rowData.getTestTime()).type(TIME),
                handler.column("custom", (rowData, cursor) -> "custom_test_" + rowData.getTestString() + "_" + rowData.getTestLong())
        ).write(repository.findAllEntity(), (rowData, cursor) -> {entityManager.detach(rowData);});
    }

}
