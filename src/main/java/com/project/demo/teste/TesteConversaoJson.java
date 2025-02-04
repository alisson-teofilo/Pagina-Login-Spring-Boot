package com.project.demo.teste;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class TesteConversaoJson {
    public static void main(String[] args) {
        String[] arrayString;

        arrayString = new String[] {"obj1", "obj2", "obj3"};

        Gson gson = new Gson();
        String arrayJson = gson.toJson(arrayString);

        boolean isJsonValid = isJSONValid(arrayJson);
    }

    public static boolean isJSONValid(String jsonString) {
        try {
            Gson gson = new Gson();
            gson.fromJson(jsonString, Object.class);
            return true;
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }
}
