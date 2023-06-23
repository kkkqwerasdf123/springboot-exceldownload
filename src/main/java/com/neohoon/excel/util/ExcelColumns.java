package com.neohoon.excel.util;

public class ExcelColumns<T> {

    private ExcelColumn<T>[] columns;

    public ExcelColumns() {
    }

    @SafeVarargs
    public final void setColumns(ExcelColumn<T>... columns) {
        this.columns = columns;
    }

    public ExcelColumn<T> get(int columnIndex) {
        if (columnIndex > columns.length - 1) {
            throw new IllegalArgumentException("");
        }
        return columns[columnIndex];
    }

    public int getSize() {
        return columns.length;
    }

    public ExcelColumn<T> column(String name, ExcelRowFunction<T, Object> function) {
        return new ExcelColumn<>(name, function);
    }
}
