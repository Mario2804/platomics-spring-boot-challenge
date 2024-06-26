package com.platomics.hiring.springboot.survey.adapter.in.web;

import com.platomics.hiring.springboot.survey.application.port.in.ImportCsvUseCase;
import com.platomics.hiring.springboot.survey.application.service.exceptions.AggregateException;
import com.platomics.hiring.springboot.survey.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * API used for the validation of CSV used by SurveyJS
 *
 * @author Mario Pirau
 */
@WebAdapter
@RestController
@RequestMapping("api/survey")
@RequiredArgsConstructor
class SurveyFileUploadController {


    private final ImportCsvUseCase importCsvUseCase;


    @PostMapping("/import/validate")
    public void doImport(@RequestParam("file") MultipartFile file) throws IOException, AggregateException {
        importCsvUseCase.importCsv(file.getInputStream());
    }
}
