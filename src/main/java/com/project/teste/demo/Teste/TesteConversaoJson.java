package com.project.teste.demo.Teste;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;

public class TesteConversaoJson {
    public static void main(String[] args) {
        String[] arrayString;

        arrayString = new String[] {"obj1", "obj2", "obj3"};

        Gson gson = new Gson();
        String arrayJson = gson.toJson(arrayString);

        System.out.println(arrayJson);

        boolean isJsonValid = isJSONValid(arrayJson);
        System.out.println("É um JSON válido? " + isJsonValid);
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
