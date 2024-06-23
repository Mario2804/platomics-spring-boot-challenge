package com.platomics.hiring.springboot.survey.adapter.out.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageData implements Serializable {

    @JsonProperty("name")
    private String name;

    @JsonProperty("elements")
    private List<ElementData> elements;
}
