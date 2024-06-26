package com.platomics.hiring.springboot.survey.adapter.out.persistance.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.platomics.hiring.springboot.survey.adapter.out.persistance.ChoiceDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonDeserialize(using = ChoiceDeserializer.class)
public class ChoiceData implements Serializable {

    private String value;
}
