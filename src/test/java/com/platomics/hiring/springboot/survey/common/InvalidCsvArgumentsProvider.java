package com.platomics.hiring.springboot.survey.common;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.io.IOException;
import java.util.stream.Stream;

import static com.platomics.hiring.springboot.survey.common.InvalidCsvProvider.getInputStream;


public class InvalidCsvArgumentsProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws IOException {

        return Stream.of(
                Arguments.of(getInputStream("ce-ivdd-and-ce-ivdr-missing-fields.csv")),
                Arguments.of(getInputStream("ce-ivdd-missing-fields.csv")),
                Arguments.of(getInputStream("ce-ivdr-missing-fields.csv")),
                Arguments.of(getInputStream("ce-mdd-missing-fields.csv")),
                Arguments.of(getInputStream("ce-mdr-missing-fields.csv")),
                Arguments.of(getInputStream("invalid-component-list-IVDD.csv")),
                Arguments.of(getInputStream("missing-name.csv")),
                Arguments.of(getInputStream("missing-name-and-ce-ivdd-missing-fields.csv"))
        );
    }
}
