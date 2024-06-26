package com.platomics.hiring.springboot.survey.adapter.out.persistance;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.VisibilityConditionData;

import java.io.IOException;


/**
 * A custom parser to retrieve the visibilityIf condition
 *
 * @author Mario Pirau
 */
public class VisibilityConditionDeserializer extends StdDeserializer<VisibilityConditionData> {

    public VisibilityConditionDeserializer() {
        this(null);
    }

    public VisibilityConditionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public VisibilityConditionData deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        JsonNode node = parser.getCodec().readTree(parser);
        String parsedValue, conditionName = "", value = "";
        String[] conditionalExpression;
        if (node.isTextual()) {
            parsedValue = node.textValue();

            conditionalExpression = parsedValue.split("=");
            if (conditionalExpression.length == 2) {
                conditionName = conditionalExpression[0].trim().replaceAll("[{}]", "");
                value = conditionalExpression[1].trim().replaceAll("'", "");
            }
        }


        return new VisibilityConditionData(conditionName, value);
    }
}
