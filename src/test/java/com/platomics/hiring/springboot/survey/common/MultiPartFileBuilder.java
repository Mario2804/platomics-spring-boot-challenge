package com.platomics.hiring.springboot.survey.common;

import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MultiPartFileBuilder {

    public static MockMultipartFile buildMultipartFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream input = new FileInputStream(file);

        return new MockMultipartFile("file", file.getName(), "text/csv", input);
    }

    public static InputStream getInputStreamOfMockMultipartFile(String filePath) throws IOException {
        return buildMultipartFile(filePath).getInputStream();
    }
}
