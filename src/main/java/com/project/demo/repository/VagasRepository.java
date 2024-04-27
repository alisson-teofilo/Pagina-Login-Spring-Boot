package com.project.demo.repository;

import com.project.demo.model.Vagas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class VagasRepository {
    NamedParameterJdbcTemplate namedJdbcTemplate;
    @Autowired
    public VagasRepository(NamedParameterJdbcTemplate namedJdbcTemplate){
        this.namedJdbcTemplate = namedJdbcTemplate;
    }
    public List<Vagas> listarVagas() throws DataAccessException {
        try {
            String sql = "SELECT TITULO, DESCRICAO, VALOR_MENSAL, LOCAL_ATUACAO, CNPJ_EMPRESA FROM ALISSON.VAGAS WHERE TITULO = 'FSKDFJKLSDJFKJSADÇLF'";

            return namedJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Vagas.class));

        } catch (DataAccessException e) {
            System.out.println("caiu");
            e.printStackTrace();
            throw new DataAccessException("Erro interno no servidor"){};
        }

    }
}
