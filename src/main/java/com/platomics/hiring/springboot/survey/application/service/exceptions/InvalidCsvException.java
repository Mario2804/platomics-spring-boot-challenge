package com.platomics.hiring.springboot.survey.application.service.exceptions;

/**
 * A custom exception used to indicate the failure of csv validation
 *
 * @author Mario Pirau
 */
public class InvalidCsvException extends RuntimeException {

    private final long rowNumber;
    private final String columnName;
    private final String message;

    public InvalidCsvException(long rowNumber, String columnName, String message) {
        // the increase is to account for the header
        this.rowNumber = rowNumber + 1;
        this.columnName = columnName;
        this.message = message;
    }

    public long getRowNumber() {
        return rowNumber;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getMessage() {
        return message;
    }
}
