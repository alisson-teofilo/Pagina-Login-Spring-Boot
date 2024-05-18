package com.project.demo.repository.sql;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Data
public class SqlUsuariosPf {

    @Getter
    private static String sql_tokenValidoRepository = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token) THEN (SELECT DATATOKEN FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token) ELSE '0' END AS DATA_TOKEN_GERADO FROM DUAL";

    @Getter
    public static String sql_consultaEmail = "SELECT CASE WHEN EXISTS (SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id) THEN (SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id) ELSE '0' END AS EMAIL FROM DUAL";

    @Getter
    private static String sql_insereTokenTabela = "INSERT INTO ALISSON.VALIDATOKEN (CODUSUARIO, TOKEN, DATATOKEN) VALUES ( :codUsuario, :token, :datatoken)";

    @Getter
    private static String sql_crateUserRepository = "INSERT INTO ALISSON.USUARIOS(ID, NOME, CPF, EMAIL, SENHA) VALUES (sqcUsuario.nextval, :nome, :cpf, :email, :senha)";

    @Getter
    private static String sql_listaUsuarioRepository = "SELECT ID, NOME FROM ALISSON.USUARIOS";

    @Getter
    private static String sql_atualizaUsuario = "UPDATE ALISSON.USUARIOS SET NOME = :nome, SENHA = :senha WHERE ID = :id ";


}
