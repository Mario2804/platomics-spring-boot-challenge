package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface ValidationRule {

    void validate(List<ElementData> rules, CSVRecord csvRecord);
}
