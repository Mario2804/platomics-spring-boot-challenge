package com.platomics.hiring.springboot.survey.adapter.out.persistance;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.SurveyData;
import com.platomics.hiring.springboot.survey.application.port.out.LoadJsonPort;
import com.platomics.hiring.springboot.survey.common.PersistenceAdapter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@PersistenceAdapter
@RequiredArgsConstructor
class JsonPersistenceAdapter implements LoadJsonPort {


    private final SurveyDataProvider surveyDataProvider;

    @Override
    public List<ElementData> loadElements() throws IOException {
        SurveyData surveyData = surveyDataProvider.get();

        if (surveyData == null || surveyData.getPages() == null) {
            return List.of();
        }

        List<ElementData> elementDataList = new ArrayList<>();

        for (var page : surveyData.getPages()) {
            if (page != null && page.getElements() != null) {
                elementDataList.addAll(page.getElements());
            }
        }

        return elementDataList;
    }
}
