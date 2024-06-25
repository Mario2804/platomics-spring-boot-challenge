package com.platomics.hiring.springboot.survey.common;

import com.platomics.hiring.springboot.survey.application.service.exceptions.ChoiceValueNotFoundException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.RequiredFieldNotException;
import com.platomics.hiring.springboot.survey.application.service.exceptions.VisibleFieldNotFoundException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.List;
import java.util.stream.Stream;

import static com.platomics.hiring.springboot.survey.common.InvalidCsvProvider.getMockMultipartFile;

public class InvalidCsvSingleIssueWithExceptionArgumentsProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        // @formatter:off

        return Stream.of(
                Arguments.of(getMockMultipartFile("ce-ivdd-missing-fields.csv"),
                        List.of(new VisibleFieldNotFoundException(23, "component_list_IVDD", "visibleIf field value not found."))),
                Arguments.of(
                        getMockMultipartFile("ce-ivdr-missing-fields.csv"),
                        List.of(new VisibleFieldNotFoundException(17, "component_risk_class_IVDR", "visibleIf field value not found."))),
                Arguments.of(
                        getMockMultipartFile("ce-mdr-missing-fields.csv"),
                        List.of(new VisibleFieldNotFoundException(28, "component_risk_class_MDR_MDD", "visibleIf field value not found."))),
                Arguments.of(
                        getMockMultipartFile("ce-mdd-missing-fields.csv"),
                        List.of(new VisibleFieldNotFoundException(25, "component_risk_class_MDR", "visibleIf field value not found."))),
                Arguments.of(
                        getMockMultipartFile("invalid-component-list-IVDD.csv"),
                        List.of(new ChoiceValueNotFoundException(22, "component_list_IVDD", "choice value not found."))),
                Arguments.of(
                        getMockMultipartFile("missing-name.csv"),
                        List.of(new RequiredFieldNotException(19, "component_name", "required field not found.")))
        );

        // @formatter:on
    }
}
