package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.model.Usuario;
import com.project.demo.repository.sql.SqlUsuariosPf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class UsuarioPfRepository {

    NamedParameterJdbcTemplate namedJdbcTemplate;
    final JavaMailSender javaMailSender;

    @Autowired
    public UsuarioPfRepository(JavaMailSender javaMailSender, NamedParameterJdbcTemplate namedJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.javaMailSender = javaMailSender;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public int cadastrarUsuario(UsuarioPfRequest usuarioPfRequest) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nome", usuarioPfRequest.getNome())
                .addValue("cpf", usuarioPfRequest.getCpf())
                .addValue("email", usuarioPfRequest.getEmail())
                .addValue("senha", usuarioPfRequest.getSenha());

        return namedJdbcTemplate.update(SqlUsuariosPf.getCadastrarUsuario(), params);
    }


    public List<Usuario> listarUsuario() {
        try {

             return namedJdbcTemplate.query(SqlUsuariosPf.getListaUsuario(), new BeanPropertyRowMapper<>(Usuario.class));

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int atualizaUsuario(UsuarioPfRequest usuarioPfRequest) {
      SqlParameterSource params = new MapSqlParameterSource()
           .addValue("nome", usuarioPfRequest.getNome(), Types.VARCHAR)
           .addValue("senha", usuarioPfRequest.getSenha(), Types.VARCHAR)
           .addValue("id", usuarioPfRequest.getId(), Types.VARCHAR);
      return namedJdbcTemplate.update(SqlUsuariosPf.getAtualizaUsuario(), params);
    }

    public void excluirUsuario(UsuarioPfRequest request){

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", request.getId(), Types.VARCHAR);

        namedJdbcTemplate.update(SqlUsuariosPf.getExcluirUsuario(), params);
    }

    public void buscarUsuarioPF(UsuarioPfRequest request){

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", request.getId(), Types.VARCHAR);

        namedJdbcTemplate.update(SqlUsuariosPf.getExcluirUsuario(), params);
    }
}



