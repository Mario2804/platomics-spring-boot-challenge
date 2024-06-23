package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.application.service.exceptions.MissingMandatoryFieldException;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RequiredFieldValidation implements ValidationRule {


    public void validate(List<ElementData> rules, CSVRecord csvRecord) {
        for (var element : rules) {
            if (element.isRequired() && element.getVisibilityCondition() == null &&
                    csvRecord.get(element.getName()).isEmpty()) {

                throw new MissingMandatoryFieldException(csvRecord.getRecordNumber(),
                        element.getName(),
                        "required field not found."
                );
            }
        }
    }
}
