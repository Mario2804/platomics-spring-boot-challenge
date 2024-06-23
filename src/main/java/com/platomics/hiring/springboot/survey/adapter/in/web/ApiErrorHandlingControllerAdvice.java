package com.platomics.hiring.springboot.survey.adapter.in.web;

import com.platomics.hiring.springboot.survey.adapter.in.web.response.ApiError;
import com.platomics.hiring.springboot.survey.adapter.in.web.response.ApiErrorResponse;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * This abstract class contains exception handlers that make sure the API returns correct error responses.
 *
 * @author Mario Pirau
 */
@RestControllerAdvice
public class ApiErrorHandlingControllerAdvice {


    private static final Logger log = LoggerFactory.getLogger(ApiErrorHandlingControllerAdvice.class);

    @ExceptionHandler(AggregateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleGenericException(AggregateException aggregateException) {

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        for (var exception : aggregateException.getExceptions()) {
            apiErrorResponse.add(new ApiError(
                    "invalid_csv",
                    exception.getRowNumber(),
                    exception.getColumnName(),
                    exception.getMessage()
            ));
        }

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleGenericException(Throwable throwable) {
        log.error("Unknown error occurred while handling API request.", throwable);

        return ResponseEntity.internalServerError().build();
    }
}
