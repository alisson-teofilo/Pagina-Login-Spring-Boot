package com.project.demo.repository.sql;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Data
@Component
public class SqlUsuariosPj {

    @Getter
    private static String sql_crateUserRepository = """
            INSERT INTO ALISSON.EMPRESAS
            (RAZAO_SOCIAL, NOME_FANTASIA, CNPJ, FUNDACAO, EMAIL , SEGMENTO , SENHA, NUMERO_FUNCIONARIOS)
            VALUES 
            (:RAZAO_SOCIAL, :NOME_FANTASIA, :CNPJ, :FUNDACAO, :EMAIL, :SEGMENTO, :SENHA, :NUMERO_FUNCIONARIOS)
            """;

    @Getter
    private static String sql_updateUserPj = """
            UPDATE ALISSON.EMPRESAS
            	SET NOME_FANTASIA  = :NOME_FANTASIA, FUNDACAO  = :FUNDACAO, EMAIL = :EMAIL, SEGMENTO = :SEGMENTO, SENHA = :SENHA, NUMERO_FUNCIONARIOS = :NUMERO_FUNCIONARIOS
            WHERE CNPJ = :CNPJ
            """;

}