package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.application.port.out.LoadJsonPort;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import com.platomics.hiring.springboot.survey.common.ElementDataProvider;
import com.platomics.hiring.springboot.survey.common.InvalidCsvArgumentsProvider;
import com.platomics.hiring.springboot.survey.common.MultiPartFileBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@Import(ElementDataProvider.class)
class ImportCsvServiceTest {


    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    @InjectMocks
    private ValidationRuleEngine validationRuleEngine;

    private final LoadJsonPort loadJsonPort = Mockito.mock(LoadJsonPort.class);
    private ImportCsvService service;
    private final ElementDataProvider elementDataProvider = new ElementDataProvider();


    @BeforeEach
    void setUp() {
        service = new ImportCsvService(loadJsonPort, validationRuleEngine);
    }

    @ParameterizedTest
    @ArgumentsSource(value = InvalidCsvArgumentsProvider.class)
    void importCsv_invalidFile_throwAggregateException(InputStream inputStream) throws IOException {
        // Arrange
        Resource resource = resourceLoader.getResource("classpath:/json/valid/valid-complete-survey.json");
        when(loadJsonPort.loadElements()).thenReturn(elementDataProvider.loadElementData(resource));

        // Act + Assert
        assertThrows(AggregateException.class, () -> service.importCsv(inputStream));
    }

    @Test
    void importCsv_validFile_noExceptionThrown() throws IOException {
        // Arrange
        Resource resource = resourceLoader.getResource("classpath:/json/valid/valid-complete-survey.json");
        when(loadJsonPort.loadElements()).thenReturn(elementDataProvider.loadElementData(resource));

        InputStream inputStream =
                MultiPartFileBuilder.getInputStreamOfMockMultipartFile("src/test/resources/csv/valid/valid.csv");

        // Act + Assert
        assertDoesNotThrow(() -> service.importCsv(inputStream));
    }
}