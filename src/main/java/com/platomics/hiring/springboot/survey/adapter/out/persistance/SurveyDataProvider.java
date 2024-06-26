package com.platomics.hiring.springboot.survey.adapter.out.persistance;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.SurveyData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * A class responsible for parsing the survey.json
 *
 * @author Mario Pirau
 */
@Component
public class SurveyDataProvider {


    @Value("classpath:survey.json")
    private Resource surveyJson;

    public SurveyData get() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(
                DeserializationFeature.READ_ENUMS_USING_TO_STRING,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );

        return mapper.readValue(surveyJson.getInputStream(), SurveyData.class);
    }
}
