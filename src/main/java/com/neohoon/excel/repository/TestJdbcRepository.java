package com.neohoon.excel.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TestJdbcRepository {

    private final JdbcTemplate template;
    private final Random random = new Random();


    public void insertTestData(int count) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into test_entity(test_string, test_long, test_integer, test_datetime, test_date, test_time, test_double, test_float) values ");
        LocalDateTime datetime = LocalDateTime.of(1991, 12, 17, 11, 12, 0);
        for (int i = 0; i < count; i++) {
            sql.append(
                    String.format(
                            "('%s', %s, %s, '%s', '%s', '%s', %s, %s)",
                            UUID.randomUUID(),
                            random.nextLong(),
                            random.nextInt(),
                            datetime = datetime.plusSeconds(random.nextInt(12000)),
                            datetime.toLocalDate(),
                            datetime.toLocalTime(),
                            random.nextDouble(),
                            random.nextFloat()
                            ));
            if (i < count - 1) {
                sql.append(", ");
            }
        }
        template.execute(sql.toString());
    }

}
