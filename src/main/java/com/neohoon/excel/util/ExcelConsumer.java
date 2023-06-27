package com.neohoon.excel.util;

@FunctionalInterface
public interface ExcelConsumer<T> {

    void accept(T rowData, ExcelCursor cursor);

}