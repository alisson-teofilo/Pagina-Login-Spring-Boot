package com.project.teste.demo.Repository;

import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
@Slf4j
@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    private final JavaMailSender javaMailSender;

    public UsuarioRepository(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String tokenValidoRepository(String token) {
        String sql = "SELECT DATATOKEN FROM ALISSON_DB.VALIDATOKEN WHERE TOKEN = :token";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("token", token);
        return namedJdbcTemplate.queryForObject(sql, params, String.class);
    }

    public String consultaEmail(Usuario modelUsuario) {
        String retorno = null;
        try {
            String sql = "SELECT EMAIL FROM ALISSON_DB.USUARIO WHERE ID = :codUsuario";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("codUsuario", modelUsuario.getId(), Types.VARCHAR);

            retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int insereTokenTabela(GeraToken classeToken, Usuario modelUsuario) {
        int retorno = 0;
        try {
            String sql2 = "INSERT INTO ALISSON_DB.VALIDATOKEN (CODUSUARIO, TOKEN, DATATOKEN) VALUES ( :codUsuario, :token, :datatoken)";
            SqlParameterSource parametro = new MapSqlParameterSource()
                    .addValue("codUsuario", modelUsuario.getId())
                    .addValue("token", classeToken.getToken())
                    .addValue("datatoken", classeToken.getExpiraToken());
            retorno = namedJdbcTemplate.update(sql2, parametro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public void disparaEmail(String link, String emailUsiario) {
       try {
           //Envia o email para o usuário
           var mensagem = new SimpleMailMessage();
           mensagem.setTo(emailUsiario);
           mensagem.setSubject("Requição troca de Email");
           mensagem.setText("Para redefinir a sua senha clique no link: " + link);
           javaMailSender.send(mensagem);
       } catch (Exception e) {
           e.printStackTrace();
       }

    }

    public int crateUserRepository(Usuario entityUser) {
      String sql = "INSERT INTO ALISSON_DB.USUARIO(ID, NOME, SENHA) VALUES (:id,:nome,:senha)";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", entityUser.getId())
                .addValue("nome", entityUser.getNome())
                .addValue("senha", entityUser.getSenha());
                return namedJdbcTemplate.update(sql, params);
    }

    public List<Usuario> listaUsuarioRepository() {
        String sql = "SELECT ID, NOME FROM ALISSON_DB.USUARIO";
        List<Usuario> usuarios = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usuario.class));
        return usuarios;
    }

    public int atualizaUsuario(Usuario usuario) {
        int retorno = 0;
        String sql =
              " UPDATE \n" +
              " USUARIO \n" +
              " SET \n" +
              " NOME = :nome \n" +
              " WHERE ID = :id \n";

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome", usuario.getNome(), Types.VARCHAR)
                .addValue("id", usuario.getId(), Types.VARCHAR);
        retorno = namedJdbcTemplate.update(sql, params);

        return retorno;
    }

    public String validaLogin(Usuario entityUser) {
        String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE EMAIL = ? AND SENHA = ?) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";
        return jdbcTemplate.queryForObject(sql, String.class, entityUser.getEmail(), entityUser.getSenha());
    }

    public String validaSenhas(Usuario usuario) {
        String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE ID = ? AND (SENHA != ? AND SENHA2 != ?)) THEN 1 ELSE 0 END AS SENHA_VALIDA FROM DUAL";
       return jdbcTemplate.queryForObject(sql, String.class, usuario.getId(), usuario.getSenha(), usuario.getSenha());
    }
}



