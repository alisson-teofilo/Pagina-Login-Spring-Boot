package com.project.demo.repository;

import com.project.demo.dto.requestDTO.LoginRequest;
import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.repository.sql.SqlLogin;
import com.project.demo.repository.sql.SqlUsuariosPf;
import com.project.demo.service.GeraToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
public class LoginRepository {

    NamedParameterJdbcTemplate namedJdbcTemplate;
    JavaMailSender javaMailSender;

    @Autowired
    public LoginRepository(NamedParameterJdbcTemplate namedJdbcTemplate, JavaMailSender javaMailSender) {
        this.namedJdbcTemplate = namedJdbcTemplate;
        this.javaMailSender = javaMailSender;
    }

    public String tokenValidoRepository(LoginRequest request) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("token", request.getToken())
                .addValue("token", request.getToken());

        return namedJdbcTemplate.queryForObject(SqlUsuariosPf.tokenValidoRepository, params, String.class);
    }

    public String consultaEmail(LoginRequest dto) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", dto.getId());

        return namedJdbcTemplate.queryForObject(SqlUsuariosPf.consultaEmail, params, String.class);
    }

    public int insereTokenTabela(GeraToken classeToken, LoginRequest dto) {

        // converte a data em String
        DateTimeFormatter formataDataString = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatadaString = classeToken.getDataToken().format(formataDataString);

        // converte a string em LocalDate
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataFormatada = LocalDate.parse(dataFormatadaString, formataData);

        SqlParameterSource parametro = new MapSqlParameterSource()
                .addValue("codUsuario", dto.getId())
                .addValue("token", classeToken.getToken())
                .addValue("datatoken", dataFormatada);

        return namedJdbcTemplate.update(SqlUsuariosPf.insereTokenTabela, parametro);
    }

    public void disparaEmail(String baseUrl, String emailUsiario, GeraToken classeToken, LoginRequest dto) {

        String paramsUrl = "?params=";
        String paramID = "&paramID=";

        //Envia o email para o usuário
        var mensagem = new SimpleMailMessage();

        mensagem.setTo(emailUsiario);
        mensagem.setSubject("Requição troca de Email");
        mensagem.setText("Para redefinir a sua senha clique no link: " + baseUrl + paramsUrl + classeToken.getToken() + paramID + dto.getId());

        javaMailSender.send(mensagem);
    }

    public String validaId(String id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id, Types.VARCHAR);

        return namedJdbcTemplate.queryForObject(SqlLogin.validaId.replace("#TIPO_IDEN#",id.length() == 14 ? "CNPJ" : "ID").replace("#TIPO_USUARIO#", id.length() == 14 ? "EMPRESAS" : "USUARIOS"), params, String.class);
    }

    public int efeturaLogin(LoginRequest dto) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", dto.getId(), Types.VARCHAR)
                .addValue("senha", dto.getSenha(), Types.VARCHAR);

        Integer result = namedJdbcTemplate.queryForObject(SqlLogin.validaLogin.replace("#TIPO_USUARIO#", dto.getId().length() == 14 ? "EMPRESAS" : "USUARIOS").replace("#TIPO_IDEN#", dto.getId().length() == 14 ? "CNPJ" : "ID"), params, Integer.class);

        return result != null ? result : 0;
    }

    public int recuperarSenhaUsuario(LoginRequest dto) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("ID", dto.getId(), Types.VARCHAR)
                .addValue("SENHA", dto.getSenha(), Types.VARCHAR);

        return namedJdbcTemplate.update(SqlLogin.atualizarSenhaUsuario, params);
    }

    public int recuperarSenhaEmpresa(LoginRequest dto) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("CNPJ", dto.getId(), Types.VARCHAR)
                .addValue("SENHA", dto.getSenha(), Types.VARCHAR);

        return namedJdbcTemplate.update(SqlLogin.atualizarSenhaEmpresa, params);

    }


}
