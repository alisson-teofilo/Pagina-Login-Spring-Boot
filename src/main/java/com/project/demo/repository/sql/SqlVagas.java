package com.project.demo.repository.sql;

import lombok.Data;
import lombok.Getter;

@Data
public class SqlVagas {

    @Getter
    private static String sql_listarVagas = "SELECT TITULO, DESCRICAO, VALOR_MENSAL, LOCAL_ATUACAO, CNPJ_EMPRESA FROM ALISSON.VAGAS";

    @Getter
    private static String sql_inserirVaga = "INSERT INTO ALISSON.VAGAS (CNPJ_EMPRESA, TITULO, DESCRICAO, DATA_PUBLICACAO, VALOR_MENSAL, LOCAL_ATUACAO) VALUES (:CNPJ_EMPRESA, :TITULO, :DESCRICAO, :DATA_PUBLICACAO, :VALOR_MENSAL, :LOCAL_ATUACAO)";

    @Getter
    private static String sql_jobUpdate = "UPDATE ALISSON.VAGAS SET TITULO  = :TITULO, DESCRICAO  = :DESCRICAO, VALOR_MENSAL = :VALOR_MENSAL, LOCAL_ATUACAO = :LOCAL_ATUACAO WHERE CNPJ_EMPRESA = :CNPJ";

    @Getter
    private static String sql_searchJobs = "SELECT TITULO, DESCRICAO, VALOR_MENSAL, DATA_PUBLICACAO, LOCAL_ATUACAO FROM ALISSON.VAGAS WHERE TITULO LIKE UPPER('%' || :PALAVRA_CHAVE || '%') OR DESCRICAO LIKE UPPER('%' || :PALAVRA_CHAVE || '%')";

}
