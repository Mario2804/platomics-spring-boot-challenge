package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.application.port.out.LoadJsonPort;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.InvalidCsvException;
import com.platomics.hiring.springboot.survey.common.InvalidCsvArgumentsProvider;
import com.platomics.hiring.springboot.survey.common.MultiPartFileBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class ImportCsvServiceTest {


    private final LoadJsonPort loadJsonPort = Mockito.mock(LoadJsonPort.class);
    private final ValidationRuleEngine validationRuleEngine = Mockito.mock(ValidationRuleEngine.class);

    private final ImportCsvService service = new ImportCsvService(loadJsonPort, validationRuleEngine);

    @ParameterizedTest
    @ArgumentsSource(value = InvalidCsvArgumentsProvider.class)
    void importCsv_invalidFile_throwAggregateException(InputStream inputStream) {
        // Arrange
        doThrow(new InvalidCsvException(0, "column_name", "message")).when(validationRuleEngine).validate(any(), any());

        // Act + Assert
        assertThrows(AggregateException.class, () -> service.importCsv(inputStream));
    }

    @Test
    void importCsv_validFile_noExceptionThrown() throws IOException {
        // Arrange
        doNothing().when(validationRuleEngine).validate(any(), any());
        InputStream inputStream =
                MultiPartFileBuilder.buildMultipartFile("src/test/resources/csv/valid/valid.csv").getInputStream();

        // Act + Assert
        assertDoesNotThrow(() -> service.importCsv(inputStream));
    }
}