package com.project.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.model.ModelJson;


public class JsonFormatter {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String formatJson(String response) throws JsonProcessingException {
        Object jsonObject = mapper.readValue(response, ModelJson.class);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
    }
}
