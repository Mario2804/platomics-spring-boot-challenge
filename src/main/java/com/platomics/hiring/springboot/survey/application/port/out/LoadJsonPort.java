package com.platomics.hiring.springboot.survey.application.port.out;

import com.platomics.hiring.springboot.survey.adapter.out.persistance.entity.ElementData;

import java.io.IOException;
import java.util.List;

public interface LoadJsonPort {

    List<ElementData> loadElements() throws IOException;
}
