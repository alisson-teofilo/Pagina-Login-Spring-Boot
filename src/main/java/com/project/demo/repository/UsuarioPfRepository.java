package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.model.Usuario;
import com.project.demo.repository.sql.SqlUsuariosPf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
@Slf4j
@Repository
public class UsuarioPfRepository {

    NamedParameterJdbcTemplate namedJdbcTemplate;
    final JavaMailSender javaMailSender;

    @Autowired
    public UsuarioPfRepository(JavaMailSender javaMailSender, NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.javaMailSender = javaMailSender;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }
        public int crateUserRepository(UsuarioPfRequest usuarioPfRequest)
    {
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("nome", usuarioPfRequest.getNome())
                    .addValue("cpf", usuarioPfRequest.getCpf())
                    .addValue("email", usuarioPfRequest.getEmail())
                    .addValue("senha", usuarioPfRequest.getSenha());

            return namedJdbcTemplate.update(SqlUsuariosPf.getSql_crateUserRepository(), params);
    }

    public List<Usuario> listaUsuarioRepository()
    {
        try {

             return namedJdbcTemplate.query(SqlUsuariosPf.getSql_listaUsuarioRepository(), new BeanPropertyRowMapper<>(Usuario.class));

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int atualizaUsuario(UsuarioPfRequest usuarioPfRequest)
    {
      SqlParameterSource params = new MapSqlParameterSource()
           .addValue("nome", usuarioPfRequest.getNome(), Types.VARCHAR)
           .addValue("senha", usuarioPfRequest.getSenha(), Types.VARCHAR)
           .addValue("id", usuarioPfRequest.getId(), Types.VARCHAR);
      return namedJdbcTemplate.update(SqlUsuariosPf.getSql_atualizaUsuario(), params);
    }
}



