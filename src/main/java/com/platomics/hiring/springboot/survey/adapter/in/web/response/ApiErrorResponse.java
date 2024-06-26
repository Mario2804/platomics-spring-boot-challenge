package com.platomics.hiring.springboot.survey.adapter.in.web.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


/**
 * An implementation containing a list of all {@link ApiError}
 *
 * @author Mario Pirau
 */
@Getter
public class ApiErrorResponse {

    private final List<ApiError> apiErrors = new ArrayList<>();

    public void add(ApiError error) {
        this.apiErrors.add(error);
    }
}
