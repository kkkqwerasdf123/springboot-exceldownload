package com.neohoon.excel.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@Slf4j
public class ExcelHandler<T> {

    private final SXSSFWorkbook wb;
    private SXSSFSheet sheet;
    private final ExcelColumns<T> columns;
    private final ExcelCursor cursor;

    private int maxRowsOfSheet = 10_000;

    public void setMaxRowsOfSheet(int maxRowsOfSheet) {
        this.maxRowsOfSheet = maxRowsOfSheet;
    }

    public ExcelHandler(String title, String subtitle, ExcelColumns<T> columns) {
        this.wb = new SXSSFWorkbook(10000);
        this.sheet = wb.createSheet();

        this.columns = columns;
        this.cursor = new ExcelCursor(columns.getSize());

        SXSSFRow titleRow = sheet.createRow(cursor.getRowOfSheet());
        CellStyle titleStyle = titleStyle(titleRow);
        SXSSFRow subTitleRow = sheet.createRow(cursor.getRowOfSheet());
        CellStyle subtitleStyle = subTitleStyle(subTitleRow);
        CellStyle headerStyle = headerStyle();
        SXSSFRow headRow = sheet.createRow(cursor.getRowOfSheet());
        cursor.plusRow();

        setTitleAndSubTitle(title, subtitle, titleRow, titleStyle, subTitleRow, subtitleStyle);
        setColumnHeaders(columns, headerStyle, headRow);
    }

    private void setColumnHeaders(ExcelColumns<T> columns, CellStyle headerStyle, SXSSFRow headRow) {
        for (int j = 0; j < cursor.getColumnCount(); j++) {
            SXSSFCell cell = headRow.createCell(j);
            ExcelColumn<T> column = columns.get(j);
            cell.setCellValue(column.getName());
            CellStyle nowStyle = wb.createCellStyle();

            nowStyle.setAlignment(column.getAlignment() == null ? HorizontalAlignment.CENTER : column.getAlignment());
            if (column.getDataFormat() != null) {
                nowStyle.setDataFormat(column.getDataFormat());
            }
            nowStyle.setBorderTop(BorderStyle.THIN);
            nowStyle.setBorderBottom(BorderStyle.THIN);
            nowStyle.setBorderLeft(BorderStyle.THIN);
            nowStyle.setBorderRight(BorderStyle.THIN);
            columns.get(j).setStyle(nowStyle);
            cell.setCellStyle(headerStyle);
        }
    }

    private void setTitleAndSubTitle(String title, String subtitle, SXSSFRow titleRow, CellStyle titleStyle, SXSSFRow subTitleRow, CellStyle subtitleStyle) {
        for (int j = 0; j < cursor.getColumnCount(); j++) {
            SXSSFCell titleRowCell = titleRow.createCell(j);
            titleRowCell.setCellValue(title);
            titleRowCell.setCellStyle(titleStyle);

            Cell subTitleRowCell = subTitleRow.createCell(j);
            subTitleRowCell.setCellValue(subtitle != null ? subtitle : "");
            subTitleRowCell.setCellStyle(subtitleStyle);
        }
    }


    private CellStyle titleStyle(SXSSFRow titleRow) {
        CellStyle titleStyle = wb.createCellStyle();
        Font titleFont = wb.createFont();
        sheet.addMergedRegion(new CellRangeAddress(cursor.getRowOfSheet(), cursor.getRowOfSheet(), 0, cursor.getColumnCount() - 1));
        cursor.plusRow();
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleFont.setBold(true);
        titleFont.setFontHeight((short) (20 * 20));
        titleStyle.setFont(titleFont);
        titleRow.setHeight((short) 1035);
        return titleStyle;
    }

    private CellStyle subTitleStyle(SXSSFRow subTitleRow) {
        CellStyle subtitleStyle = wb.createCellStyle();
        Font subtitleFont = wb.createFont();
        sheet.addMergedRegion(new CellRangeAddress(cursor.getRowOfSheet(), cursor.getRowOfSheet(), 0, cursor.getColumnCount() - 1));
        cursor.plusRow();
        subTitleRow.setHeight((short) 630);
        subtitleStyle.setAlignment(HorizontalAlignment.RIGHT);
        subtitleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        subtitleFont.setBold(true);
        subtitleFont.setFontHeight((short) (11 * 20));
        subtitleStyle.setFont(subtitleFont);
        return subtitleStyle;
    }

    private CellStyle headerStyle() {
        CellStyle headerStyle = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerFont.setBold(true);
        headerFont.setFontHeight((short) (11 * 20));
        headerFont.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
        headerStyle.setFont(headerFont);
        return headerStyle;
    }

    public void download(String fileName, HttpServletResponse response) throws IOException {
        response.setHeader("Set-Cookie", "fileDownload=true; path=/");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(String.format("%s.xlsx", fileName), StandardCharsets.UTF_8)));
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        try (ServletOutputStream stream = response.getOutputStream(); OutputStream out = new BufferedOutputStream(stream)) {
            response.resetBuffer();
            response.setBufferSize(1024 * 4);
            wb.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wb != null) {
            try {
                wb.dispose();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                wb.close();
            }
        }
    }

    public void write(Stream<T> stream) {
        stream.forEach(this::handleRowData);
    }

    private void handleRowData(T rowData) {
        cursor.plusTotal();
        if (isOverMaxRows()) {
            turnOverSheet();
        }
        SXSSFRow row = sheet.createRow(cursor.getRowOfSheet());
        cursor.plusRow();

        for (int j = 0; j < cursor.getColumnCount(); j++) {
            SXSSFCell cell = row.createCell(j);
            ExcelColumn<T> column = columns.get(j);
            Object columnData = columns.get(j).applyFunction(rowData, cursor);
            String columnDataStr = String.valueOf(columnData);
            setColumnData(cell, column, columnData, columnDataStr);
            cell.setCellStyle(column.getStyle());
            if (sheet.getColumnWidth(j) < columnDataStr.length() * 450) {
                sheet.setColumnWidth(j, Math.min(255 * 256, columnDataStr.length() * 450 + 1024));
            }
        }
    }

    private void setColumnData(SXSSFCell cell, ExcelColumn<T> column, Object columnData, String columnDataStr) {
        try {
            switch (column.getDataType()) {
                case LONG:
                    cell.setCellValue((Long) columnData);
                    break;
                case INTEGER:
                    cell.setCellValue((Integer) columnData);
                    break;
                case DOUBLE:
                case DOUBLE_PERCENT:
                    cell.setCellValue((Double) columnData);
                    break;
                case FLOAT:
                case FLOAT_PERCENT:
                    cell.setCellValue((Float) columnData);
                    break;
                case DATETIME:
                    cell.setCellValue((LocalDateTime) columnData);
                    break;
                case DATE:
                    cell.setCellValue((LocalDate) columnData);
                    break;
                case TIME:
                case STRING:
                default:
                    cell.setCellValue(columnDataStr);
                    break;
            }
        } catch (Exception e) {
            log.debug("cast error: {}", e.getMessage());
            cell.setCellValue(String.valueOf(columnData));
        }
    }

    private void turnOverSheet() {
        this.sheet = wb.createSheet();
        this.cursor.initRow();
    }

    private boolean isOverMaxRows() {
        return cursor.getCurrentTotal() >= maxRowsOfSheet && cursor.getCurrentTotal() % maxRowsOfSheet == 1;
    }

}
