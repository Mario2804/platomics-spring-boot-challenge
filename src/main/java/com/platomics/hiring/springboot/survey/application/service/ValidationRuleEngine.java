package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ValidationRuleEngine {


    private final List<ValidationRule> validationRules;

    public void validate(List<ElementData> rules, CSVRecord csvRecord) {
        for (var rule : validationRules) {
            rule.validate(rules, csvRecord);
        }
    }
}
