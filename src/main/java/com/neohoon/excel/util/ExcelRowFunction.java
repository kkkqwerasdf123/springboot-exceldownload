package com.neohoon.excel.util;

@FunctionalInterface
public interface ExcelRowFunction<T, R> {
    R apply(T rowData, ExcelCursor cursor);
}
