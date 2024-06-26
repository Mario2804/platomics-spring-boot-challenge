package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ChoiceData;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.application.service.exceptions.ChoiceValueNotFoundException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.VisibleFieldNotFoundException;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * An {@link ValidationRule} implementation which validates the visibleIf field and the choices
 *
 * @author Mario Pirau
 */
@Component
public class VisibleConditionValidation implements ValidationRule {


    public void validate(List<ElementData> rules, CSVRecord csvRecord) {
        for (var element : rules) {
            if (!element.isRequired() || element.getVisibilityCondition() == null) {
                continue;
            }

            var visibilityCondition = element.getVisibilityCondition();
            if (visibilityCondition.getValue().equals(csvRecord.get(visibilityCondition.getComponentName()))) {

                if (csvRecord.get(element.getName()).isEmpty()) {
                    throw new VisibleFieldNotFoundException(csvRecord.getRecordNumber(),
                            element.getName(),
                            "visibleIf field value not found."
                    );
                }

                if (!validateChoices(element.getChoices(), csvRecord.get(element.getName()))) {
                    throw new ChoiceValueNotFoundException(csvRecord.getRecordNumber(),
                            element.getName(),
                            "choice value not found."
                    );
                }
            }
        }
    }

    private boolean validateChoices(List<ChoiceData> choices, String choiceValue) {
        return choices.contains(new ChoiceData(choiceValue));
    }
}
