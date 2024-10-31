package com.project.demo.repository;

import com.project.demo.dto.requestDTO.UsuarioPjRequest;
import com.project.demo.model.UsuarioPF;
import com.project.demo.model.UsuarioPJ;
import com.project.demo.repository.sql.SqlUsuariosPj;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;

@Repository
public class UsuarioPjRepository {

    NamedParameterJdbcTemplate jdbcTemplate;

    public UsuarioPjRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int crateUserRepository(UsuarioPjRequest request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("RAZAO_SOCIAL", request.getRazaoSocial())
                .addValue("NOME_FANTASIA", request.getNomeFantasia())
                .addValue("CNPJ", request.getCnpj())
                .addValue("EMAIL", request.getEmail())
                .addValue("SEGMENTO", request.getSegmento())
                .addValue("SENHA", request.getSenha());

        return jdbcTemplate.update(SqlUsuariosPj.crateUserRepository, params);
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

        return jdbcTemplate.update(SqlUsuariosPj.updateUserPj, params);
    }

    public UsuarioPJ buscarUsuarioPJ(String cnpj) {
         SqlParameterSource params = new MapSqlParameterSource()
                 .addValue("CNPJ", cnpj);

         return jdbcTemplate.queryForObject(SqlUsuariosPj.buscarUsuario, params, new BeanPropertyRowMapper<>(UsuarioPJ.class));

    }
}
