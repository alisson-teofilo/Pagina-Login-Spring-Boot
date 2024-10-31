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

    public List<Vagas> listarVagas() {
        return namedJdbcTemplate.query(SqlVagas.listarVagas, new BeanPropertyRowMapper<>(Vagas.class));
    }

    public List<Vagas> listarCandidaturasByUsuaio(String cpfUsuario) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("CPF_USUARIO", cpfUsuario);
        return namedJdbcTemplate.query(SqlVagas.listarCandidaturasByUsuaio, params, new BeanPropertyRowMapper<>(Vagas.class));
    }

    public void cadastrarVagas(VagasRequestDTO request) {

        SqlParameterSource params = new MapSqlParameterSource()

                        .addValue("CNPJ_EMPRESA",request.getCnpjEmpresa())
                        .addValue("TITULO",request.getTitulo())
                        .addValue("DESCRICAO",request.getDescricao())
                        .addValue("DATA_PUBLICACAO", new Date())
                        .addValue("VALOR_MENSAL",request.getValorMensal())
                        .addValue("LOCAL_ATUACAO",request.getLocalAtuacao());

        namedJdbcTemplate.update(SqlVagas.inserirVaga, params);
    }

    public void candidatarVaga(VagasRequestDTO request) {
        SqlParameterSource params = new MapSqlParameterSource()
                        .addValue("CPF_USUARIO",request.getCpfUsuario())
                        .addValue("ID_VAGA",Integer.parseInt(request.getIdVaga()));

        namedJdbcTemplate.update(SqlVagas.candidatarVaga, params);
    }

    public void deletarCandidatura(VagasRequestDTO request) {
        SqlParameterSource params = new MapSqlParameterSource()
                        .addValue("ROWID",request.getROWID());

        namedJdbcTemplate.update(SqlVagas.deletarCandidatura, params);
    }

    public int editarVagas(VagasRequestDTO vagasReqeuest) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("TITULO",vagasReqeuest.getTitulo())
                .addValue("DESCRICAO",vagasReqeuest.getDescricao())
                .addValue("SENIORIDADE", vagasReqeuest.getJobLevel())
                .addValue("VALOR_MENSAL",vagasReqeuest.getValorMensal())
                .addValue("ID_VAGA",vagasReqeuest.getIdVaga());
        return namedJdbcTemplate.update(SqlVagas.jobUpdate, params);
    }

    public List<Vagas> buscarVagas(VagasRequestDTO request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("DESCRICAO", request.getDescricao());

        return namedJdbcTemplate.query(SqlVagas.buscarVagas, params, new BeanPropertyRowMapper<>(Vagas.class));
    }

    public List<Vagas> vagasPublicadas(String cnpjEmpresa) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("CNPJ_EMPRESA", cnpjEmpresa);
        return namedJdbcTemplate.query(SqlVagas.vagasPublicadas, params, new BeanPropertyRowMapper<>(Vagas.class));
    }
}





