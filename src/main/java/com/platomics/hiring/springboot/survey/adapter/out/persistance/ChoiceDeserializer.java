package com.platomics.hiring.springboot.survey.adapter.out.persistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ChoiceData;

import java.io.IOException;

/**
 * A custom parser to retrieve the data from the field choice
 *
 * @author Mario Pirau
 */
public class ChoiceDeserializer extends StdDeserializer<ChoiceData> {

    public ChoiceDeserializer() {
        this(null);
    }

    public ChoiceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ChoiceData deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String value = "";

        if (node.isTextual()) {
            value = node.textValue();
        } else if (node.isObject()) {
            value = String.valueOf(node.get("value"));
        }

        return new ChoiceData(value);
    }
}
