package com.neohoon.excel.service;

import com.neohoon.excel.domain.TestEntity;
import com.neohoon.excel.dto.TestDto;
import com.neohoon.excel.repository.TestRepository;
import com.neohoon.excel.util.ExcelColumns;
import com.neohoon.excel.util.ExcelHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.neohoon.excel.util.ExcelDataType.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelService {

    private final TestRepository repository;

    @Transactional(readOnly = true)
    public ExcelHandler<TestDto> byDto() {
        ExcelColumns<TestDto> columns = new ExcelColumns<>();
        columns.setColumns(
                columns.column("no", (rowData, cursor) -> cursor.getCurrentTotal()).type(INTEGER),
                columns.column("string", (rowData, cursor) -> rowData.getTestString()).type(STRING),
                columns.column("long", (rowData, cursor) -> rowData.getTestLong()).type(LONG),
                columns.column("integer", (rowData, cursor) -> rowData.getTestInteger()).type(INTEGER),
                columns.column("double", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE),
                columns.column("float", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT),
                columns.column("double_percent", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE_PERCENT),
                columns.column("float_percent", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT_PERCENT),
                columns.column("datetime", (rowData, cursor) -> rowData.getTestDatetime()).type(DATETIME),
                columns.column("date", (rowData, cursor) -> rowData.getTestDate()).type(DATE),
                columns.column("time", (rowData, cursor) -> rowData.getTestTime()).type(TIME),
                columns.column("custom", (rowData, cursor) -> "custom_test_" + rowData.getTestString() + "_" + rowData.getTestLong())
        );
        ExcelHandler<TestDto> handler = new ExcelHandler<>("test", "test...sub", columns);
        handler.write(repository.findAllDto());
        return handler;
    }

    @Transactional(readOnly = true)
    public ExcelHandler<TestEntity> byEntity() {
        ExcelColumns<TestEntity> columns = new ExcelColumns<>();
        columns.setColumns(
                columns.column("no", (rowData, cursor) -> cursor.getCurrentTotal()).type(INTEGER),
                columns.column("string", (rowData, cursor) -> rowData.getTestString()).type(STRING),
                columns.column("long", (rowData, cursor) -> rowData.getTestLong()).type(LONG),
                columns.column("integer", (rowData, cursor) -> rowData.getTestInteger()).type(INTEGER),
                columns.column("double", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE),
                columns.column("float", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT),
                columns.column("double_percent", (rowData, cursor) -> rowData.getTestDouble()).type(DOUBLE_PERCENT),
                columns.column("float_percent", (rowData, cursor) -> rowData.getTestFloat()).type(FLOAT_PERCENT),
                columns.column("datetime", (rowData, cursor) -> rowData.getTestDatetime()).type(DATETIME),
                columns.column("date", (rowData, cursor) -> rowData.getTestDate()).type(DATE),
                columns.column("time", (rowData, cursor) -> rowData.getTestTime()).type(TIME),
                columns.column("custom", (rowData, cursor) -> "custom_test_" + rowData.getTestString() + "_" + rowData.getTestLong())
        );
        ExcelHandler<TestEntity> handler = new ExcelHandler<>("test", "test...sub", columns);
        handler.write(repository.findAllEntity());
        return handler;
    }

}
