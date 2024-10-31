package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.dto.responseDTO.UsuarioPfResponseDTO;
import com.project.demo.model.UsuarioPF;
import com.project.demo.repository.sql.SqlUsuariosPf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    public UsuarioPfRepository(JavaMailSender javaMailSender, NamedParameterJdbcTemplate namedJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.javaMailSender = javaMailSender;
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public int cadastrarUsuario(UsuarioPfRequest usuarioPfRequest) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("NOME", usuarioPfRequest.getNome())
                .addValue("EMAIL", usuarioPfRequest.getEmail())
                .addValue("CPF", usuarioPfRequest.getCpf())
                .addValue("CARGO_ATUAL", usuarioPfRequest.getCpf())
                .addValue("SENHA", usuarioPfRequest.getSenha());

        return namedJdbcTemplate.update(SqlUsuariosPf.cadastrarUsuario, params);
    }

    public List<UsuarioPF> listarUsuario() {
        try {

             return namedJdbcTemplate.query(SqlUsuariosPf.listaUsuario, new BeanPropertyRowMapper<>(UsuarioPF.class));

        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public int atualizaUsuario(UsuarioPfRequest usuarioPfRequest) {
      SqlParameterSource params = new MapSqlParameterSource()
           .addValue("NOME", usuarioPfRequest.getNome(), Types.VARCHAR)
           .addValue("EMAIL", usuarioPfRequest.getEmail(), Types.VARCHAR)
           .addValue("SENHA", usuarioPfRequest.getSenha(), Types.VARCHAR)
           .addValue("CARGO_ATUAL", usuarioPfRequest.getCargoAtual(), Types.VARCHAR)
           .addValue("ID", usuarioPfRequest.getId(), Types.VARCHAR);

      return namedJdbcTemplate.update(SqlUsuariosPf.atualizaUsuario, params);
    }

    public void excluirUsuario(UsuarioPfRequest request){

        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", request.getId(), Types.VARCHAR);

        namedJdbcTemplate.update(SqlUsuariosPf.excluirUsuario, params);
    }


    public UsuarioPF buscarUsuario(String id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("ID", id, Types.VARCHAR);

        return namedJdbcTemplate.queryForObject(SqlUsuariosPf.buscarusuario, params, new BeanPropertyRowMapper<>(UsuarioPF.class));
    }
}



