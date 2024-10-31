package com.project.demo.repository.sql;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Data
public class SqlUsuariosPf {

    public static String tokenValidoRepository = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token) THEN (SELECT DATATOKEN FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token) ELSE '0' END AS DATA_TOKEN_GERADO FROM DUAL";

    public static String consultaEmail = "SELECT CASE WHEN EXISTS (SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id) THEN (SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id) ELSE '0' END AS EMAIL FROM DUAL";

    public static String insereTokenTabela = "INSERT INTO ALISSON.VALIDATOKEN (CODUSUARIO, TOKEN, DATATOKEN) VALUES ( :codUsuario, :token, :datatoken)";

    public static String cadastrarUsuario = "INSERT INTO ALISSON.USUARIOS(ID, NOME, CPF, EMAIL, SENHA, CARGO_ATUAL) VALUES (INCREMENTA_ID_USUARIO_SEQ.nextval, :NOME, :CPF, :EMAIL, :SENHA, :CARGO_ATUAL)";

    public static String listaUsuario = "SELECT ID, NOME FROM ALISSON.USUARIOS";

    public static String atualizaUsuario = """
            UPDATE ALISSON.USUARIOS u
            SET u.NOME = :NOME,
            u.EMAIL = :EMAIL,
            u.SENHA = :SENHA,
            u.CARGO_ATUAL = :CARGO_ATUAL
            WHERE ID = :ID
            """;

    public static String excluirUsuario = "DELETE FROM ALISSON.USUARIOS WHERE ID = :ID";

    public static String buscarusuario = "SELECT * FROM ALISSON.USUARIOS WHERE ID = :ID";
}
