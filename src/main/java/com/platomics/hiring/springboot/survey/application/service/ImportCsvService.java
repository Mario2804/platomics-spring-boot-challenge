package com.platomics.hiring.springboot.survey.application.service;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;
import com.platomics.hiring.springboot.survey.application.port.in.ImportCsvUseCase;
import com.platomics.hiring.springboot.survey.application.port.out.LoadJsonPort;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.InvalidCsvException;
import com.platomics.hiring.springboot.survey.common.UseCase;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Service used to import the csv and validate it based on the survey.json
 *
 * @author Mario Pirau
 */
@UseCase
@RequiredArgsConstructor
public class ImportCsvService implements ImportCsvUseCase {


    private final LoadJsonPort loadJsonPort;
    private final ValidationRuleEngine validationRuleEngine;

    @Override
    public void importCsv(InputStream stream) throws IOException, AggregateException {
        var aggregateException = new AggregateException();
        List<ElementData> elements = loadJsonPort.loadElements();

        var csvParser = new CSVParser(new InputStreamReader(stream), CSVFormat.DEFAULT.builder().setHeader().build());

        for (CSVRecord csvRecord : csvParser.getRecords()) {
            try {
                validationRuleEngine.validate(elements, csvRecord);
            } catch (InvalidCsvException invalidCsvException) {
                aggregateException.addException(invalidCsvException);
            }
        }

        if (aggregateException.hasExceptions()) {
            throw aggregateException;
        }
    }
}
