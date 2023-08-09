package com.project.teste.demo.Service;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UsuarioService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    private UsuarioRepository repository;

    private final JavaMailSender javaMailSender;

    public void validaToken(String token, Usuario modelUsuario, GeraToken classeToken) {

        String dataTokenUsuario = repository.tokenValidoRepository(token);

        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
        LocalDateTime dataFormatada = LocalDateTime.parse(dataTokenUsuario, formataData);

        if(!classeToken.ehTokenValido(dataFormatada)){
            System.out.println("Token Válido");
        } else {
            System.out.println("Token expirado");
        }

    }

    public void enviarEmail(Usuario modelUsuario, GeraToken classeToken){
        try {
            // URL usada para trocar a senha
            String link = "http://localhost:9000/cadastro";

            // Consulta o email do usuário logado
            String emailUsiario = repository.consultaEmail(modelUsuario);

            //Cria o registro em tabel do token gerado
            int insereDadosTabela = repository.insereTokenTabela(classeToken, modelUsuario);

            // Dispara o Email
           repository.disparaEmail(link, emailUsiario);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public UsuarioService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public int createUserService(Usuario entityUser) {
        int retornoRepository = repository.crateUserRepository(entityUser);
        return retornoRepository;
    }

    public List<Usuario> listaUsuarioService() {
        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();
        return retornoConsulta;
    }

    public ResponseEntity<UsuarioRespose> loginUserService(Usuario entityUser) {
        UsuarioRespose loginResponse = new UsuarioRespose();

        String retornoLogin = repository.validaLogin(entityUser);

        if (retornoLogin.equals("1")){
            loginResponse.setSucesso(true);
            loginResponse.setMensagem("Login efetuado com sucesso");
        } else{
            loginResponse.setSucesso(false);
            loginResponse.setMensagem("Credenciais inválidas");
        }

        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    public ResponseEntity<UsuarioRespose> atualizaUsuario(Usuario usuario) throws RuntimeException {
        UsuarioRespose response = new UsuarioRespose();

        try {
            String validaSenhas = repository.validaSenhas(usuario);

            if (validaSenhas.equals("1")){
                response.setSucesso(true);
                response.setMensagem("Atualização realizada com sucesso. ");
            }else{
                response.setSucesso(false);
                response.setMensagem("Erro. A senha ja foi utilizada. ");
            }
            repository.atualizaUsuario(usuario);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
