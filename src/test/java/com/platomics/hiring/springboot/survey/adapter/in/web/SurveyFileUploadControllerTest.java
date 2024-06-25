package com.platomics.hiring.springboot.survey.adapter.in.web;

import com.platomics.hiring.springboot.survey.application.port.in.ImportCsvUseCase;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.InvalidCsvException;
import com.platomics.hiring.springboot.survey.common.InvalidCsvMultipleIssuesWithExceptionArgumentsProvider;
import com.platomics.hiring.springboot.survey.common.InvalidCsvSingleIssueWithExceptionArgumentsProvider;
import com.platomics.hiring.springboot.survey.common.MultiPartFileBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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
    @ArgumentsSource(InvalidCsvSingleIssueWithExceptionArgumentsProvider.class)
    void importCsv_givenInvalidCsvWithSingleIssue_returns400(MockMultipartFile multipartFile,
                                                             List<InvalidCsvException> exceptions) throws Exception {
        // Arrange
        doThrow(buildAggregateException(exceptions)).when(importCsvUseCase).importCsv(any());

        // Act + Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/survey/import/validate").file(multipartFile))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.apiErrors", hasSize(1)))
                .andExpect(jsonPath("$.apiErrors[0].errorType", is("invalid_csv")))
                .andExpect(jsonPath("$.apiErrors[0].status", is("error")))
                .andExpect(jsonPath("$.apiErrors[0].errorMessage", is(exceptions.get(0).getMessage())))
                .andExpect(jsonPath("$.apiErrors[0].apiErrorDetail.rowNumber",
                        is(exceptions.get(0).getRowNumber()),
                        Long.class
                ))
                .andExpect(jsonPath("$.apiErrors[0].apiErrorDetail.columnName", is(exceptions.get(0).getColumnName())));
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidCsvMultipleIssuesWithExceptionArgumentsProvider.class)
    void importCsv_givenInvalidCsvWithMultipleIssues_returns400(MockMultipartFile multipartFile,
                                                                List<InvalidCsvException> exceptions) throws Exception {
        // Arrange
        doThrow(buildAggregateException(exceptions)).when(importCsvUseCase).importCsv(any());

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

    private AggregateException buildAggregateException(List<InvalidCsvException> exceptions) {
        var aggregateException = new AggregateException();
        exceptions.forEach(aggregateException::addException);

        return aggregateException;
    }
}