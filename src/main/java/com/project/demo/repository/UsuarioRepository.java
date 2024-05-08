package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioRequestDTO;
import com.project.demo.model.Usuario;
import com.project.demo.service.GeraToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Slf4j
@Repository
public class UsuarioRepository {

    NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JavaMailSender javaMailSender;
    String sql;

    @Autowired
    public UsuarioRepository(JavaMailSender javaMailSender, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.javaMailSender = javaMailSender;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public String tokenValidoRepository(UsuarioRequestDTO usuarioRequestDTO)
    {
        sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token) THEN (SELECT DATATOKEN FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token) ELSE '0' END AS DATA_TOKEN_GERADO FROM DUAL";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("token", usuarioRequestDTO.getToken())
                    .addValue("token", usuarioRequestDTO.getToken());

            return namedJdbcTemplate.queryForObject(sql, params, String.class);
    }

    public String consultaEmail(UsuarioRequestDTO usuarioRequestDTO)
    {
        sql = "SELECT CASE WHEN EXISTS (SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id) THEN (SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id) ELSE '0' END AS EMAIL FROM DUAL";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId());

           return namedJdbcTemplate.queryForObject(sql, params, String.class);
    }

    public int insereTokenTabela(GeraToken classeToken, UsuarioRequestDTO usuarioRequestDTO)
    {
            // converte a data em String
            DateTimeFormatter formataDataString = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatadaString = classeToken.getDataToken().format(formataDataString);

            // converte a string em LocalDate
            DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormatada = LocalDate.parse(dataFormatadaString, formataData);

            String sql2 = "INSERT INTO ALISSON.VALIDATOKEN (CODUSUARIO, TOKEN, DATATOKEN) VALUES ( :codUsuario, :token, :datatoken)";

            SqlParameterSource parametro = new MapSqlParameterSource()
                    .addValue("codUsuario", usuarioRequestDTO.getId())
                    .addValue("token", classeToken.getToken())
                    .addValue("datatoken", dataFormatada);

            return namedJdbcTemplate.update(sql2, parametro);
    }

    public void disparaEmail(String baseUrl, String emailUsiario,GeraToken classeToken)
    {
           String paramsUrl = "?params=";

           //Envia o email para o usuário
           var mensagem = new SimpleMailMessage();
           mensagem.setTo(emailUsiario);
           mensagem.setSubject("Requição troca de Email");
           mensagem.setText("Para redefinir a sua senha clique no link: " + baseUrl + paramsUrl + classeToken.getToken());
           javaMailSender.send(mensagem);
    }

    public int crateUserRepository(UsuarioRequestDTO usuarioRequestDTO)
    {
        sql = "INSERT INTO ALISSON.USUARIOS(ID, NOME, CPF, EMAIL, SENHA) VALUES (sqcUsuario.nextval, :nome, :cpf, :email, :senha)";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nome", usuarioRequestDTO.getNome())
                    .addValue("cpf", usuarioRequestDTO.getCpf())
                    .addValue("email", usuarioRequestDTO.getEmail())
                    .addValue("senha", usuarioRequestDTO.getSenha());

            return namedJdbcTemplate.update(sql, params);
    }

    public List<Usuario> listaUsuarioRepository()
    {
        try {
             sql = "SELECT ID, NOME FROM ALISSON.USUARIOS";
             return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Usuario.class));

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int validaLogin(UsuarioRequestDTO usuarioRequestDTO)
    {
        sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.USUARIOS WHERE ID = :id AND SENHA = :senha) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";

        SqlParameterSource params = new MapSqlParameterSource()
               .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR)
               .addValue("senha", usuarioRequestDTO.getSenha(), Types.VARCHAR);

        Integer result = namedJdbcTemplate.queryForObject(sql, params, Integer.class);

        return result != null ? result : 0;
    }

    public String validaId(UsuarioRequestDTO usuarioRequestDTO)
    {
        sql = "SELECT CASE WHEN EXISTS(SELECT ID FROM ALISSON.USUARIOS WHERE ID = :id) THEN (SELECT ID FROM ALISSON.USUARIOS WHERE ID = :id) ELSE '0' END AS ID FROM DUAL";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);

               return namedJdbcTemplate.queryForObject(sql, params, String.class);
    }

    public int atualizaUsuario(UsuarioRequestDTO usuarioRequestDTO)
    {
        sql = " UPDATE \n" +
            " ALISSON.USUARIOS \n" +
            " SET \n" +
            " NOME = :nome, \n"+
            " SENHA3 = SENHA2, \n" +
            " SENHA2 = SENHA, \n" +
            " SENHA = :senha \n" +
            " WHERE ID = :id ";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nome", usuarioRequestDTO.getNome(), Types.VARCHAR)
                    .addValue("senha", usuarioRequestDTO.getSenha(), Types.VARCHAR)
                    .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);
            return namedJdbcTemplate.update(sql, params);
    }
}



