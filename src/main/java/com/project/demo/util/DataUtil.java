package com.project.demo.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;


public class DataUtil {

    public static Date converteData(String data) throws ParseException {

        String[] dataVetor = data.split("/");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("pt-BR"));

        //Converte String em Date
        return sdf.parse(dataVetor[2] + "-" + dataVetor[1] + "-" + dataVetor[0]);
    }
}
