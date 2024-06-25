package com.platomics.hiring.springboot.survey.adapter.in.web;

import com.platomics.hiring.springboot.survey.application.port.in.ImportCsvUseCase;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.ChoiceValueNotFoundException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.InvalidCsvException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.RequiredFieldNotException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.VisibleFieldNotFoundException;
import com.platomics.hiring.springboot.survey.common.MultiPartFileBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SurveyFileUploadController.class)
class SurveyFileUploadControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImportCsvUseCase importCsvUseCase;

    @Test
    void importCsv_validCsv_returns200() throws Exception {
        // Arrange
        var multipartFile = MultiPartFileBuilder.buildMultipartFile("src/test/resources/csv/valid/valid.csv");

        // Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/survey/import/validate").file(multipartFile))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("provideCsvAndOneException")
    void importCsv_givenInvalidCsvWithOneIssue_returnsOneAggregated400(String fileName, InvalidCsvException exception)
            throws Exception {
        // Arrange
        var multipartFile = MultiPartFileBuilder.buildMultipartFile("src/test/resources/csv/invalid/" + fileName);

        var aggregateException = new AggregateException();
        aggregateException.addException(exception);

        doThrow(aggregateException).when(importCsvUseCase).importCsv(any());

        // Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/survey/import/validate").file(multipartFile))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.apiErrors", hasSize(1)))
                .andExpect(jsonPath("$.apiErrors[0].errorType", is("invalid_csv")))
                .andExpect(jsonPath("$.apiErrors[0].status", is("error")))
                .andExpect(jsonPath("$.apiErrors[0].errorMessage", is(exception.getMessage())))
                .andExpect(jsonPath("$.apiErrors[0].apiErrorDetail.rowNumber",
                        is(exception.getRowNumber()),
                        Long.class
                ))
                .andExpect(jsonPath("$.apiErrors[0].apiErrorDetail.columnName", is(exception.getColumnName())));
    }

    private static Stream<Arguments> provideCsvAndOneException() {
        // @formatter:off

        return Stream.of(
                Arguments.of("ce-ivdd-missing-fields.csv", new VisibleFieldNotFoundException(23, "component_list_IVDD", "visibleIf field value not found.")),
                Arguments.of("ce-ivdr-missing-fields.csv", new VisibleFieldNotFoundException(17, "component_risk_class_IVDR", "visibleIf field value not found.")),
                Arguments.of("ce-mdr-missing-fields.csv", new VisibleFieldNotFoundException(28, "component_risk_class_MDR_MDD", "visibleIf field value not found.")),
                Arguments.of("ce-mdr-missing-fields.csv", new VisibleFieldNotFoundException(25, "component_risk_class_MDR", "visibleIf field value not found.")),
                Arguments.of("invalid-component-list-IVDD.csv", new ChoiceValueNotFoundException(22, "component_list_IVDD", "choice value not found.")),
                Arguments.of("missing-name.csv", new RequiredFieldNotException(19, "component_name", "required field not found."))
        );

        // @formatter:on
    }

    @ParameterizedTest
    @MethodSource("provideCsvAndMultipleException")
    void importCsv_givenInvalidCsvWithMultipleIssues_returns400(String fileName, List<InvalidCsvException> exceptions)
            throws Exception {
        // Arrange
        var multipartFile = MultiPartFileBuilder.buildMultipartFile("src/test/resources/csv/invalid/" + fileName);

        var aggregateException = new AggregateException();
        exceptions.forEach(aggregateException::addException);

        doThrow(aggregateException).when(importCsvUseCase).importCsv(any());

        // Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/survey/import/validate").file(multipartFile))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.apiErrors", hasSize(2)))
                .andExpect(jsonPath("$.apiErrors[0].errorType", is("invalid_csv")))
                .andExpect(jsonPath("$.apiErrors[0].status", is("error")))
                .andExpect(jsonPath("$.apiErrors[0].errorMessage", is(exceptions.get(0).getMessage())))
                .andExpect(jsonPath("$.apiErrors[0].apiErrorDetail.rowNumber",
                        is(exceptions.get(0).getRowNumber()),
                        Long.class
                ))
                .andExpect(jsonPath("$.apiErrors[0].apiErrorDetail.columnName", is(exceptions.get(0).getColumnName())))
                .andExpect(jsonPath("$.apiErrors[1].errorType", is("invalid_csv")))
                .andExpect(jsonPath("$.apiErrors[1].status", is("error")))
                .andExpect(jsonPath("$.apiErrors[1].errorMessage", is(exceptions.get(1).getMessage())))
                .andExpect(jsonPath("$.apiErrors[1].apiErrorDetail.rowNumber",
                        is(exceptions.get(1).getRowNumber()),
                        Long.class
                ))
                .andExpect(jsonPath("$.apiErrors[1].apiErrorDetail.columnName", is(exceptions.get(1).getColumnName())));
    }

    private static Stream<Arguments> provideCsvAndMultipleException() {
        // @formatter:off

        return Stream.of(
                Arguments.of("ce-ivdd-and-ce-ivdr-missing-fields.csv",
                        List.of(new RequiredFieldNotException(17, "component_risk_class_IVDR", "visibleIf field value not found."),
                        new VisibleFieldNotFoundException(23, "component_list_IVDD", "visibleIf field value not found."))),
                Arguments.of("missing-name-and-ce-ivdd-missing-fields.csv",
                        List.of(new RequiredFieldNotException(19, "component_name", "required field not found."),
                                new VisibleFieldNotFoundException(23, "component_list_IVDD", "visibleIf field value not found.")))
        );

        // @formatter:on
    }
}