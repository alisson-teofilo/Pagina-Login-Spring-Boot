package com.project.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.dto.JobsDTO;
import com.project.demo.model.ModelJson;

import java.util.List;

public class JsonFormatter {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static List<JobsDTO> formatJson(String response) throws JsonProcessingException {
        ModelJson modelJson = mapper.readValue(response, ModelJson.class);
        return modelJson.getJobs();
    }
}
