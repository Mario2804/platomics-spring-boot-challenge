package com.platomics.hiring.springboot.survey.adapter.out.persistance;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.SurveyData;
import com.platomics.hiring.springboot.survey.application.port.out.LoadJsonPort;
import com.platomics.hiring.springboot.survey.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
class JsonPersistenceAdapter implements LoadJsonPort {


    @Value("classpath:survey.json")
    private Resource surveyJson;

    @Override
    public List<ElementData> loadElements() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING,
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
        );

        SurveyData surveyData = mapper.readValue(surveyJson.getInputStream(), SurveyData.class);

        List<ElementData> elementDataList = new ArrayList<>();
        for (var page : surveyData.getPages()) {
            elementDataList.addAll(page.getElements());
        }

        return elementDataList;
    }
}
