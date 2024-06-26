package com.platomics.hiring.springboot.survey.adapter.out.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElementData implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("isRequired")
    private boolean isRequired;

    @JsonProperty("visibleIf")
    private VisibilityConditionData visibilityCondition;

    @JsonProperty("choices")
    private List<ChoiceData> choices;
}
