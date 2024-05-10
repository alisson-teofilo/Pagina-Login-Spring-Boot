package com.project.demo.repository;

import com.project.demo.dto.requestDTO.VagasRequestDTO;
import com.project.demo.model.Vagas;
import com.project.demo.repository.sql.SqlVagas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public class VagasRepository {

    NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public VagasRepository(NamedParameterJdbcTemplate namedJdbcTemplate)
    {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<Vagas> listarVagas()
    {
        return namedJdbcTemplate.query(SqlVagas.getSql_listarVagas(), new BeanPropertyRowMapper<>(Vagas.class));
    }

    public void inserirVaga(VagasRequestDTO request)
    {
        SqlParameterSource params = new MapSqlParameterSource()
                        .addValue("CNPJ_EMPRESA",request.getCnpjEmpresa())
                        .addValue("TITULO",request.getTitulo())
                        .addValue("DESCRICAO",request.getDescricao())
                        .addValue("DATA_PUBLICACAO", new Date())
                        .addValue("VALOR_MENSAL",request.getValorMensal())
                        .addValue("LOCAL_ATUACAO",request.getLocalAtuacao());

        namedJdbcTemplate.update(SqlVagas.getSql_inserirVaga(), params);
    }
}
