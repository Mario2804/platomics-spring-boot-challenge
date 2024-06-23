package com.platomics.hiring.springboot.survey.application.port.in;

import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;

import java.io.IOException;
import java.io.InputStream;

public interface ImportCsvUseCase {

    void importCsv(InputStream stream) throws IOException, AggregateException;
}
