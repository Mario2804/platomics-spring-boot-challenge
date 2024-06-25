package com.platomics.hiring.springboot.survey.adapter.in.web.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * An {@link ApiResponse} implementation indicating failure.
 *
 * @author Mario Pirau
 */
@Getter
public class ApiError implements ApiResponse {

    private final String errorType;
    private final String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final ApiErrorDetail apiErrorDetail;

    public ApiError(String errorType, String errorMessage) {
        this(errorType, errorMessage, null);
    }

    public ApiError(String errorType, String errorMessage, ApiErrorDetail apiErrorDetail) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.apiErrorDetail = apiErrorDetail;
    }

    @Override
    public String getStatus() {
        return "error";
    }
}
