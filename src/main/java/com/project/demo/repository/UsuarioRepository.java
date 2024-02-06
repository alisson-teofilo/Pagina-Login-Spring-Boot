package com.project.demo.repository;

import com.project.demo.dto.UsuarioRequestDTO;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedJdbcTemplate;

    private final JavaMailSender javaMailSender;

    public UsuarioRepository(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String tokenValidoRepository(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException {
        try {
            String sql = "SELECT DATATOKEN FROM ALISSON.VALIDATOKEN WHERE TOKEN = :token";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("token", usuarioRequestDTO.getToken());
            return namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            throw new DataAccessException("Erro de validação") {};
        }
    }

    public String consultaEmail(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException {
        try {
            String sql = "SELECT EMAIL FROM ALISSON.USUARIOS WHERE ID = :id";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId());

           return namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e) {
            throw new DataAccessException("Erro no servidor interno") {};
        }
    }

    public int insereTokenTabela(GeraToken classeToken, UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException {
        try {
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
        } catch (DataAccessException e) {
            throw new DataAccessException("Erro interno no servidor") {};
        }
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

    public int crateUserRepository(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException {
        try {
            String sql = "INSERT INTO ALISSON.USUARIOS(ID, NOME, SENHA) VALUES (:id,:nome,:senha)";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId())
                    .addValue("nome", usuarioRequestDTO.getNome())
                    .addValue("senha", usuarioRequestDTO.getSenha());
            return namedJdbcTemplate.update(sql, params);
        } catch (DataAccessException e){
            throw new DataAccessException("Erro interno no servidor"){};
        }
    }

    public List<Usuario> listaUsuarioRepository() throws DataAccessException {
        try {
            String sql = "SELECT ID, NOME FROM ALISSON.USUARIOS";
             return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Usuario.class));
        } catch (DataAccessException e){
            throw new DataAccessException("Erro interno no servidor"){};
        }
    }

    public String validaRe(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException {
        try {
            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.USUARIOS WHERE ID = :id) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);
            return namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Erro ao validar"){};
        }
    }

    public String validaLogin(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException {
        try {
            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.USUARIOS WHERE ID = :id AND SENHA = :senha) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR)
                    .addValue("senha", usuarioRequestDTO.getSenha(), Types.VARCHAR);
            return namedJdbcTemplate.queryForObject(sql, params, String.class);
        } catch (DataAccessException e){
            e.printStackTrace();
            throw new DataAccessException("Erro ao validar"){};
        }
    }

    public String validaId(UsuarioRequestDTO usuarioRequestDTO) {
        try {

            String sql = "SELECT ID FROM ALISSON.USUARIOS WHERE ID = :id";

            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);

               return namedJdbcTemplate.queryForObject(sql, params, String.class);

        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public String validaSenhas(UsuarioRequestDTO usuarioRequestDTO) {

            try {
                String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON.USUARIOS WHERE ID = ? AND (SENHA != ? AND SENHA2 != ?)) THEN 1 ELSE 0 END AS SENHA_VALIDA FROM DUAL";
                return jdbcTemplate.queryForObject(sql, String.class, usuarioRequestDTO.getId(), usuarioRequestDTO.getSenha(), usuarioRequestDTO.getSenha());
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        return null;
    }

    public int atualizaUsuario(UsuarioRequestDTO usuarioRequestDTO)  throws DataAccessException{

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
                    .addValue("nome", usuarioRequestDTO.getNome(), Types.VARCHAR)
                    .addValue("senha", usuarioRequestDTO.getSenha(), Types.VARCHAR)
                    .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);
            return namedJdbcTemplate.update(sql, params);
        } catch (DataAccessException e) {
            throw new DataAccessException("Erro interno no servidor"){};
        }
    }
}



