package com.platomics.hiring.springboot.survey.application.service.exceptions;


/**
 * An {@link InvalidCsvException} implementation indicating the source of failure.
 *
 * @author Mario Pirau
 */
public class ChoiceValueNotValidException extends InvalidCsvException {

    public ChoiceValueNotValidException(long rowNumber, String columnName, String message) {
        super(rowNumber, columnName, message);
    }
}
