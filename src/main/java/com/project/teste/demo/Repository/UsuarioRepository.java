package com.project.teste.demo.Repository;

import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

    public String tokenValidoRepository(Usuario modelUsuario) {
        String retorno = null;
        try {
            String sql = "SELECT DATATOKEN FROM ALISSON_DB.VALIDATOKEN WHERE TOKEN = :token";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("token", modelUsuario.getToken());
            retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            e.printStackTrace();
        }
        return retorno;
    }

    public String consultaEmail(Usuario modelUsuario) {
        String retorno = null;
        try {
            String sql = "SELECT EMAIL FROM ALISSON_DB.USUARIO WHERE ID = :codUsuario";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("codUsuario", modelUsuario.getId(), Types.VARCHAR);

            retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int insereTokenTabela(GeraToken classeToken, Usuario modelUsuario) {
        int retorno = 0;
        try {
            // converte a data em String
            DateTimeFormatter formataDataString = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatadaString = classeToken.getDataToken().format(formataDataString);

            // converte a string em LocalDate
            DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormatada = LocalDate.parse(dataFormatadaString, formataData);

            String sql2 = "INSERT INTO ALISSON_DB.VALIDATOKEN (CODUSUARIO, TOKEN, DATATOKEN) VALUES ( :codUsuario, :token, :datatoken)";

            SqlParameterSource parametro = new MapSqlParameterSource()
                    .addValue("codUsuario", modelUsuario.getId())
                    .addValue("token", classeToken.getToken())
                    .addValue("datatoken", dataFormatada);
            retorno = namedJdbcTemplate.update(sql2, parametro);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    public void disparaEmail(String baseUrl, String emailUsiario,GeraToken classeToken) {
       try {
           String paramsUrl = "?params=";
           //Envia o email para o usuário
           var mensagem = new SimpleMailMessage();
           mensagem.setTo(emailUsiario);
           mensagem.setSubject("Requição troca de Email");
           mensagem.setText("Para redefinir a sua senha clique no link: " + baseUrl + paramsUrl + classeToken.getToken());
           javaMailSender.send(mensagem);
       } catch (MailException e) {
           e.printStackTrace();
       }

    }

    public int crateUserRepository(Usuario entityUser) {
        int retorno = 0;
        try {
            String sql = "INSERT INTO ALISSON_DB.USUARIO(ID, NOME, SENHA) VALUES (:id,:nome,:senha)";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", entityUser.getId())
                    .addValue("nome", entityUser.getNome())
                    .addValue("senha", entityUser.getSenha());
            retorno = namedJdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            e.printStackTrace();
        }
        return retorno;
    }

    public List<Usuario> listaUsuarioRepository() {
        List<Usuario> usuarios = null;
        try {
            String sql = "SELECT ID, NOME FROM ALISSON_DB.USUARIO";
             usuarios = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usuario.class));
        } catch (DataAccessException e){
            e.printStackTrace();
        }
       return usuarios;
    }

    public int atualizaUsuario(Usuario usuario) {
        int retorno = 0;
        try {
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
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public String validaLogin(Usuario entityUser) {
        String retorno = null;
        try {
            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE EMAIL = ? AND SENHA = ?) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";
            retorno = jdbcTemplate.queryForObject(sql, String.class, entityUser.getEmail(), entityUser.getSenha());
        } catch (DataAccessException e){
            e.printStackTrace();
        }
        return retorno;
    }

    public String validaSenhas(Usuario usuario) {
        String retorno = null;
        try {
            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE ID = ? AND (SENHA != ? AND SENHA2 != ?)) THEN 1 ELSE 0 END AS SENHA_VALIDA FROM DUAL";
            retorno = jdbcTemplate.queryForObject(sql, String.class, usuario.getId(), usuario.getSenha(), usuario.getSenha());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return retorno;
    }
}



