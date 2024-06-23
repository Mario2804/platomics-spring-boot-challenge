package com.platomics.hiring.springboot.survey.adapter.in.web.response;


import lombok.Getter;

/**
 * An {@link ApiResponse} implementation indicating failure.
 *
 * @author Mario Pirau
 */
@Getter
public class ApiError implements ApiResponse {

    private String errorType;

    private long rowNumber;
    private String columnName;
    private String reason;

    public ApiError(String errorType, long rowNumber, String columnName, String reason) {
        this.errorType = errorType;
        this.rowNumber = rowNumber;
        this.columnName = columnName;
        this.reason = reason;
    }

    @Override
    public String getStatus() {
        return "error";
    }
}
