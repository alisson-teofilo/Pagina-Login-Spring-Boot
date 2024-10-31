package com.project.demo.repository.sql;

import lombok.Data;
import lombok.Getter;

@Data
public class SqlVagas {

    public static String listarVagas = "SELECT TITULO, DESCRICAO, VALOR_MENSAL, LOCAL_ATUACAO, CNPJ_EMPRESA FROM ALISSON.VAGAS";

    public static String listarCandidaturasByUsuaio = """
            SELECT
                c.ROWID,
                v.ID AS ID_VAGA,
                v.TITULO,
                v.DESCRICAO,
                v.DATA_PUBLICACAO,
                v.VALOR_MENSAL,
                c.DATA_CANDIDATURA
            FROM
                ALISSON.CANDIDATURAS c
            JOIN
                ALISSON.VAGAS v ON c.ID_VAGA = v.ID
            JOIN
                ALISSON.USUARIOS u ON c.CPF_USUARIO = u.CPF
            WHERE
                u.CPF = :CPF_USUARIO
            """;

    public static String inserirVaga = "INSERT INTO ALISSON.VAGAS (ID, CNPJ_EMPRESA, TITULO, DESCRICAO, DATA_PUBLICACAO, VALOR_MENSAL) VALUES (ALISSON.INCREMENTA_ID_SEQ.NEXTVAL, :CNPJ_EMPRESA, :TITULO, :DESCRICAO, :DATA_PUBLICACAO, :VALOR_MENSAL)";

    public static String candidatarVaga = """
            INSERT INTO ALISSON.CANDIDATURAS (ID, CPF_USUARIO, ID_VAGA, DATA_CANDIDATURA)
            VALUES (ALISSON.INCREMENTA_ID_SEQ.NEXTVAL, :CPF_USUARIO, :ID_VAGA, SYSDATE)
            """;

    public static String jobUpdate = "UPDATE ALISSON.VAGAS SET TITULO  = :TITULO, DESCRICAO  = :DESCRICAO, SENIORIDADE = :SENIORIDADE, VALOR_MENSAL = :VALOR_MENSAL WHERE ID = :ID_VAGA";

    public static String buscarVagas = "SELECT id as ID_VAGA, NOME_EMPRESA as companyName, TITULO, DESCRICAO, VALOR_MENSAL, DATA_PUBLICACAO, SENIORIDADE AS jobLevel FROM ALISSON.VAGAS WHERE TITULO LIKE UPPER('%' || :DESCRICAO || '%') OR :DESCRICAO LIKE UPPER('%' || :DESCRICAO || '%')";

    public static String vagasPublicadas = "SELECT v.id as ID_VAGA, v.SENIORIDADE as jobLevel, v.* FROM ALISSON.VAGAS v WHERE v.CNPJ_EMPRESA = :CNPJ_EMPRESA";

    public static String deletarCandidatura = "DELETE ALISSON.CANDIDATURAS c WHERE ROWID = :ROWID";
}
