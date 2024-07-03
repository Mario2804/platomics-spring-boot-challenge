package com.platomics.hiring.springboot.survey.common;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.SurveyData;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElementDataProvider {

    private ObjectMapper mapper;

    public ElementDataProvider() {
        this.mapper = new ObjectMapper();
        this.mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );
    }

    public List<ElementData> loadElementData(Resource json) throws IOException {
        List<ElementData> result = new ArrayList<>();
        loadSurveyData(json).getPages().forEach(pageData -> result.addAll(pageData.getElements()));

        return result;
    }

    public SurveyData loadSurveyData(Resource json) throws IOException {
        return mapper.readValue(json.getInputStream(), SurveyData.class);
    }
}
