package com.project.demo.repository;

import com.project.demo.model.Vagas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class VagasRepository {

    NamedParameterJdbcTemplate namedJdbcTemplate;
    String sql;

    @Autowired
    public VagasRepository(NamedParameterJdbcTemplate namedJdbcTemplate){
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Vagas> listarVagas() {

        sql = "SELECT TITULO, DESCRICAO, VALOR_MENSAL, LOCAL_ATUACAO, CNPJ_EMPRESA FROM ALISSON.VAGAS";

        return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vagas.class));

    }
}
