package com.platomics.hiring.springboot.survey.adapter.out.persistance.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyData implements Serializable {

    @JsonProperty("pages")
    private List<PageData> pages;
}
