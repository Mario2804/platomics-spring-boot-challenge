package com.platomics.hiring.springboot.survey.application.service.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom exception is used to collect all InvalidCsvException
 *
 * @author Mario Pirau
 */
public class AggregateException extends Exception {


    private final List<InvalidCsvException> exceptions = new ArrayList<>();

    public boolean hasExceptions() {
        return !this.exceptions.isEmpty();
    }

    public void addException(InvalidCsvException invalidCsvException) {
        addSuppressed(invalidCsvException);
        exceptions.add(invalidCsvException);
    }

    public List<InvalidCsvException> getExceptions() {
        return exceptions;
    }
}
