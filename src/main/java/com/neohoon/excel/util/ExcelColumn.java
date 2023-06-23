package com.neohoon.excel.util;

import lombok.Getter;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Getter
public class ExcelColumn<T> {

    private final String name;
    private ExcelDataType dataType = ExcelDataType.STRING;
    private Short dataFormat;
    private HorizontalAlignment alignment = HorizontalAlignment.CENTER;
    private final ExcelRowFunction<T, Object> function;
    private CellStyle style;

    protected ExcelColumn(String name, ExcelRowFunction<T, Object> function) {
        this.name = name;
        this.function = function;
    }

    public ExcelColumn<T> type(ExcelDataType dataType) {
        this.dataType = dataType;
        if (dataFormat == null) {
            if (dataType.equals(ExcelDataType.LONG) || dataType.equals(ExcelDataType.INTEGER)) {
                this.format(HSSFDataFormat.getBuiltinFormat("#,##0"));
            } else if (dataType.equals(ExcelDataType.DOUBLE_PERCENT) || dataType.equals(ExcelDataType.FLOAT_PERCENT)) {
                this.format(HSSFDataFormat.getBuiltinFormat("0.00%"));
            } else if (dataType.equals(ExcelDataType.DATE)) {
                this.format(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            } else if (dataType.equals(ExcelDataType.DATETIME)) {
                this.format(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
            } else if (dataType.equals(ExcelDataType.TIME)) {
                this.format(HSSFDataFormat.getBuiltinFormat("h:mm"));
            }
        }
        return this;
    }

    public ExcelColumn<T> format(Short dataFormat) {
        this.dataFormat = dataFormat;
        return this;
    }

    public ExcelColumn<T> alignment(HorizontalAlignment alignment) {
        this.alignment = alignment;
        return this;
    }

    protected Object applyFunction(T rowData, ExcelCursor cursor) {
        return function.apply(rowData, cursor);
    }

    protected void setStyle(CellStyle style) {
        this.style = style;
    }
}
