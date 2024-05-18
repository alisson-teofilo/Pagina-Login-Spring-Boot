package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.dto.requestDTO.UsuarioPjRequest;
import com.project.demo.model.Usuario;
import com.project.demo.repository.sql.SqlUsuariosPf;
import com.project.demo.repository.sql.SqlUsuariosPj;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class UsuarioPjRepository {

    NamedParameterJdbcTemplate jdbcTemplate;

    public UsuarioPjRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int crateUserRepository(UsuarioPjRequest request)
    {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("RAZAO_SOCIAL", request.getRazaoSocial())
                .addValue("NOME_FANTASIA", request.getNomeFantasia())
                .addValue("CNPJ", request.getCnpj())
                .addValue("FUNDACAO", request.getFundacao())
                .addValue("EMAIL", request.getEmail())
                .addValue("SEGMENTO", request.getSegmento())
                .addValue("SENHA", request.getSenha())
                .addValue("NUMERO_FUNCIONARIOS", request.getNumeroFuncionario()
                );

        return jdbcTemplate.update(SqlUsuariosPj.getSql_crateUserRepository(), params);
    }

    public int updateUserPj(UsuarioPjRequest request) {

        SqlParameterSource params = new MapSqlParameterSource()

                .addValue("CNPJ", request.getCnpj())
                .addValue("NOME_FANTASIA", request.getNomeFantasia())
                .addValue("FUNDACAO", request.getFundacao())
                .addValue("EMAIL", request.getEmail())
                .addValue("SEGMENTO", request.getSegmento())
                .addValue("SENHA", request.getSenha())
                .addValue("NUMERO_FUNCIONARIOS", request.getNumeroFuncionario()
                );

        return jdbcTemplate.update(SqlUsuariosPj.getSql_updateUserPj(), params);
    }
}
