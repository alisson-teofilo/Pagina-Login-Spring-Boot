package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioRequestDTO;
import com.project.demo.model.Usuario;
import com.project.demo.repository.sql.SqlUsuarios;
import com.project.demo.service.GeraToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
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

    @Autowired
    public UsuarioRepository(JavaMailSender javaMailSender, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.javaMailSender = javaMailSender;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public String tokenValidoRepository(UsuarioRequestDTO usuarioRequestDTO)
    {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("token", usuarioRequestDTO.getToken())
                    .addValue("token", usuarioRequestDTO.getToken());

            return namedJdbcTemplate.queryForObject(SqlUsuarios.getSql_tokenValidoRepository(), params, String.class);
    }

    public String consultaEmail(UsuarioRequestDTO usuarioRequestDTO)
    {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", usuarioRequestDTO.getId());

           return namedJdbcTemplate.queryForObject(SqlUsuarios.getSql_consultaEmail(), params, String.class);
    }

    public int insereTokenTabela(GeraToken classeToken, UsuarioRequestDTO usuarioRequestDTO)
    {
            // converte a data em String
            DateTimeFormatter formataDataString = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatadaString = classeToken.getDataToken().format(formataDataString);

            // converte a string em LocalDate
            DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataFormatada = LocalDate.parse(dataFormatadaString, formataData);

            SqlParameterSource parametro = new MapSqlParameterSource()
                    .addValue("codUsuario", usuarioRequestDTO.getId())
                    .addValue("token", classeToken.getToken())
                    .addValue("datatoken", dataFormatada);

            return namedJdbcTemplate.update(SqlUsuarios.getSql_insereTokenTabela(), parametro);
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
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nome", usuarioRequestDTO.getNome())
                    .addValue("cpf", usuarioRequestDTO.getCpf())
                    .addValue("email", usuarioRequestDTO.getEmail())
                    .addValue("senha", usuarioRequestDTO.getSenha());

            return namedJdbcTemplate.update(SqlUsuarios.getSql_crateUserRepository(), params);
    }

    public List<Usuario> listaUsuarioRepository()
    {
        try {

             return namedJdbcTemplate.query(SqlUsuarios.getSql_listaUsuarioRepository(), new BeanPropertyRowMapper<>(Usuario.class));

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int validaLogin(UsuarioRequestDTO usuarioRequestDTO)
    {
        SqlParameterSource params = new MapSqlParameterSource()
               .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR)
               .addValue("senha", usuarioRequestDTO.getSenha(), Types.VARCHAR);

        Integer result = namedJdbcTemplate.queryForObject(SqlUsuarios.getSql_validaLogin(), params, Integer.class);

        return result != null ? result : 0;
    }

    public String validaId(UsuarioRequestDTO usuarioRequestDTO)
    {
       SqlParameterSource params = new MapSqlParameterSource()
             .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);

       return namedJdbcTemplate.queryForObject(SqlUsuarios.getSql_validaId(), params, String.class);
    }

    public int atualizaUsuario(UsuarioRequestDTO usuarioRequestDTO)
    {
      SqlParameterSource params = new MapSqlParameterSource()
           .addValue("nome", usuarioRequestDTO.getNome(), Types.VARCHAR)
           .addValue("senha", usuarioRequestDTO.getSenha(), Types.VARCHAR)
           .addValue("id", usuarioRequestDTO.getId(), Types.VARCHAR);
      return namedJdbcTemplate.update(SqlUsuarios.getSql_atualizaUsuario(), params);
    }
}



