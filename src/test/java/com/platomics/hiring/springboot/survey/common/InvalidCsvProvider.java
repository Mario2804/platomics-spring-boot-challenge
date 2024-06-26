package com.platomics.hiring.springboot.survey.common;

import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;

public class InvalidCsvProvider {

    public static InputStream getInputStream(String fileName) throws IOException {
        return getMockMultipartFile(fileName).getInputStream();
    }

    public static MockMultipartFile getMockMultipartFile(String fileName) throws IOException {
        return MultiPartFileBuilder.buildMultipartFile("src/test/resources/csv/invalid/" + fileName);
    }
}
