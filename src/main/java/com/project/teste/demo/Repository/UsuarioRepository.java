package com.project.teste.demo.Repository;

import com.project.teste.demo.Dto.DtoResponse;
import com.project.teste.demo.Exception.NotFound;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.SaveOrUpdateEvent;
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

    public String tokenValidoRepository(Usuario modelUsuario) throws DataAccessException {
        String retorno = null;
        try {
            String sql = "SELECT DATATOKEN FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("token", modelUsuario.getToken());
            retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            throw new DataAccessException("Erro de validação") {};
        }
        return retorno;
    }

    public String consultaEmail(Usuario modelUsuario) throws DataAccessException {
        String retorno = null;
        try {
            String sql = "SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", modelUsuario.getId());

           retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e) {
            throw new DataAccessException("Erro no servidor interno") {};
        }

        return retorno;
    }

    public int insereTokenTabela(GeraToken classeToken, Usuario modelUsuario) throws DataAccessException {
        int retorno = 0;
        try {
            // converte a data em String
            DateTimeFormatter formataDataString = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatadaString = classeToken.getDataToken().format(formataDataString);

            // converte a string em LocalDate
            DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormatada = LocalDate.parse(dataFormatadaString, formataData);

            String sql2 = "INSERT INTO ALISSON.VALIDATOKEN (CODUSUARIO, TOKEN, DATATOKEN) VALUES ( :codUsuario, :token, :datatoken)";

            SqlParameterSource parametro = new MapSqlParameterSource()
                    .addValue("codUsuario", modelUsuario.getId())
                    .addValue("token", classeToken.getToken())
                    .addValue("datatoken", dataFormatada);
            retorno = namedJdbcTemplate.update(sql2, parametro);
        } catch (DataAccessException e) {
            throw new DataAccessException("Erro interno no servidor") {};
        }
        return retorno;
    }

    public void disparaEmail(String baseUrl, String emailUsiario,GeraToken classeToken) throws MailException {
       try {
           String paramsUrl = "?params=";
           //Envia o email para o usuário
           var mensagem = new SimpleMailMessage();
           mensagem.setTo(emailUsiario);
           mensagem.setSubject("Requição troca de Email");
           mensagem.setText("Para redefinir a sua senha clique no link: " + baseUrl + paramsUrl + classeToken.getToken());
           javaMailSender.send(mensagem);
       } catch (MailException e) {
           throw new MailException("Erro ao enviar Email"){};
       }
    }

    public int crateUserRepository(Usuario entityUser) throws DataAccessException {
        int retorno = 0;
        try {
            String sql = "INSERT INTO ALISSON.USUARIOS(ID, NOME, SENHA) VALUES (:id,:nome,:senha)";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", entityUser.getId())
                    .addValue("nome", entityUser.getNome())
                    .addValue("senha", entityUser.getSenha());
            retorno = namedJdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            throw new DataAccessException("Erro interno no servidor"){};
        }
        return retorno;
    }

    public List<Usuario> listaUsuarioRepository() throws DataAccessException {
        List<Usuario> usuarios = null;
        try {
            String sql = "SELECT ID, NOME FROM ALISSON.USUARIOS";
             usuarios = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usuario.class));
        } catch (DataAccessException e){
            throw new DataAccessException("Erro interno no servidor"){};
        }
       return usuarios;
    }

    public String validaRe(Usuario entityUser) throws DataAccessException {
        String retorno = null;
        try {
            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM USUARIOS WHERE ID = :id) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", entityUser.getId(), Types.VARCHAR);
            retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Erro ao validar"){};
        }
        return retorno;
    }

    public String validaLogin(Usuario entityUser) throws DataAccessException {
        String retorno = null;
        try {
            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM USUARIOS WHERE ID = :id AND SENHA = :senha) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", entityUser.getId(), Types.VARCHAR)
                    .addValue("senha", entityUser.getSenha(), Types.VARCHAR);
            retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Erro ao validar"){};
        }
        return retorno;
    }

    public String validaId(Usuario usuario) {
        String retorno = null;
        try {
            String sql = "SELECT ID FROM ALISSON.USUARIOS WHERE ID = :id";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuario.getId(), Types.VARCHAR);
               retorno = namedJdbcTemplate.queryForObject(sql, params, String.class);
        }catch (DataAccessException e){
            e.printStackTrace();
        }
        return retorno;
    }

    public String validaSenhas(Usuario usuario) {
        String retorno = null;
        try {
            try {
                String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.USUARIOS WHERE ID = ? AND (SENHA != ? AND SENHA2 != ?)) THEN 1 ELSE 0 END AS SENHA_VALIDA FROM DUAL";
                retorno = jdbcTemplate.queryForObject(sql, String.class, usuario.getId(), usuario.getSenha(), usuario.getSenha());
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        } catch (DataAccessException e){
            e.printStackTrace();
        }
        return retorno;
    }

    public int atualizaUsuario(Usuario usuario)  throws DataAccessException{
        int retorno = 0;
        try {
            String sql =
                    " UPDATE \n" +
                    " ALISSON.USUARIOS \n" +
                    " SET \n" +
                    " NOME = :nome, \n"+
                    " SENHA3 = SENHA2, \n" +
                    " SENHA2 = SENHA, \n" +
                    " SENHA = :senha \n" +
                    " WHERE ID = :id ";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nome", usuario.getNome(), Types.VARCHAR)
                    .addValue("senha", usuario.getSenha(), Types.VARCHAR)
                    .addValue("id", usuario.getId(), Types.VARCHAR);
            retorno = namedJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            throw new DataAccessException("Erro interno no servidor"){};
        }

        return retorno;
    }
}



