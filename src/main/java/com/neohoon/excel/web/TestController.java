package com.neohoon.excel.web;

import com.neohoon.excel.domain.TestEntity;
import com.neohoon.excel.dto.TestDto;
import com.neohoon.excel.repository.TestJdbcRepository;
import com.neohoon.excel.repository.TestRepository;
import com.neohoon.excel.service.ExcelService;
import com.neohoon.excel.util.ExcelHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;

@RestController
public class TestController {

    private final TestJdbcRepository jdbcRepository;
    private final TestRepository repository;
    private final ExcelService excelService;

    public TestController(TestJdbcRepository jdbcRepository, TestRepository repository, ExcelService excelService) {
        this.jdbcRepository = jdbcRepository;
        this.repository = repository;
        this.excelService = excelService;
    }

    @PostMapping("/test-data")
    public ResponseEntity<Void> insertTestData(Integer count) {
        if (count < 1 || count > 100000) {
            return ResponseEntity.badRequest().build();
        }
        jdbcRepository.insertTestData(count);
        return ResponseEntity.created(URI.create("/")).build();
    }

    @DeleteMapping("/test-data")
    public ResponseEntity<Void> clearTestData() {
        jdbcRepository.clearData();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/total-data")
    public ResponseEntity<Long> totalData() {
        return ResponseEntity.ok(repository.count());
    }

    @GetMapping("/download/excel/dto")
    public void excelByDto(HttpServletResponse response) throws IOException {
        ExcelHandler<TestDto> handler = excelService.byDto();
        handler.download(LocalDate.now()+ "_file_name_test", response);
    }

    @GetMapping("/download/excel/entity")
    public void excelByEntity(HttpServletResponse response) throws IOException {
        ExcelHandler<TestEntity> handler = excelService.byEntity();
        handler.download(LocalDate.now()+ "_file_name_test", response);
    }
}
