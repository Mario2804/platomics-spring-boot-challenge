package com.platomics.hiring.springboot.survey.adapter.out.persistance.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.VisibilityConditionDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonDeserialize(using = VisibilityConditionDeserializer.class)
public class VisibilityConditionData implements Serializable {


    private String componentName;
    private String value;
}
