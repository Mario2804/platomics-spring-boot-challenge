package com.platomics.hiring.springboot.survey.adapter.out.persistance;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.common.ElementDataProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


@SpringBootTest
class JsonPersistenceAdapterTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @MockBean
    private SurveyDataProvider surveyDataProvider;

    @Autowired
    private JsonPersistenceAdapter persistenceAdapterTest;

    private final ElementDataProvider elementDataProvider = new ElementDataProvider();


    @ParameterizedTest
    @MethodSource("provideValidJson")
    void loadElements_validJsonFile_returnListWithOneElement(String jsonName, int numberOfElements) throws IOException {
        // Arrange
        Resource resource = resourceLoader.getResource("classpath:/json/valid/" + jsonName);
        when(surveyDataProvider.get()).thenReturn(elementDataProvider.loadSurveyData(resource));

        // Act
        List<ElementData> result = persistenceAdapterTest.loadElements();

        // Assert
        assertEquals(numberOfElements, result.size());
    }

    private static Stream<Arguments> provideValidJson() {
        return Stream.of(Arguments.of("valid-simple-survey.json", 1), Arguments.of("valid-complete-survey.json", 8));
    }

    @ParameterizedTest
    @ValueSource(strings = { "invalid-simple-survey.json", "invalid-complete-survey.json" })
    void loadElements_invalidJsonFile_returnEmptyList(String jsonName) throws IOException {
        // Arrange
        Resource resource = resourceLoader.getResource("classpath:/json/invalid/" + jsonName);
        when(surveyDataProvider.get()).thenReturn(elementDataProvider.loadSurveyData(resource));

        // Act
        List<ElementData> result = persistenceAdapterTest.loadElements();

        // Assert
        assertTrue(result.isEmpty());
    }
}