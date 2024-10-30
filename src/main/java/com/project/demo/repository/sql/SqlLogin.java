package com.project.demo.repository.sql;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Data
@Component
public class SqlLogin {

    public static String validaLogin = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.#TIPO_USUARIO# WHERE #TIPO_IDEN# = :id AND SENHA = :senha) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";

    public static String validaId = "SELECT CASE WHEN EXISTS(SELECT #TIPO_IDEN# FROM ALISSON.#TIPO_USUARIO# WHERE #TIPO_IDEN# = :id) THEN (SELECT #TIPO_IDEN# FROM ALISSON.#TIPO_USUARIO# WHERE #TIPO_IDEN# = :id) ELSE '0' END AS ID FROM DUAL";

}
