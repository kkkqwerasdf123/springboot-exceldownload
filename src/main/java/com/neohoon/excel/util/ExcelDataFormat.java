package com.neohoon.excel.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;

public enum ExcelDataFormat {

    NUMBER(HSSFDataFormat.getBuiltinFormat("#,##0")),
    PERCENT(HSSFDataFormat.getBuiltinFormat("0.00%")),
    DATETIME(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm")),
    DATE(HSSFDataFormat.getBuiltinFormat("m/d/yy")),
    TIME(HSSFDataFormat.getBuiltinFormat("h:mm")),
    ;

    private final short format;

    ExcelDataFormat(short format) {
        this.format = format;
    }

    public short getFormat() {
        return format;
    }
}
