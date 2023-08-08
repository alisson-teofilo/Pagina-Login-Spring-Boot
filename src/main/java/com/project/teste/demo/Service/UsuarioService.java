package com.project.teste.demo.Service;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GeraToken geraToken;

    private final JavaMailSender javaMailSender;

    public void validaToken(String token, String codUsuario) {
        String sql = "SELECT DATATOKEN FROM ALISSON_DB.USUARIO WHERE TOKEN = :token";

    }

    public void enviarEmail( String para, String titulo, String codUsuario){

        // URL usada para trocar a senha
        String link = "http://localhost:9000/cadastro";

        // Gera o token
        System.out.println("token: " + geraToken.getToken());
        System.out.println("Hora atual: " + LocalDateTime.now());
        System.out.println("Hora token: "+ geraToken.getExpiraToken()); ;

        // Consulta o email do usuário logado
        String sql = "SELECT EMAIL FROM ALISSON_DB.USUARIO WHERE ID = :codUsuario";
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("codUsuario", codUsuario, Types.VARCHAR);

        String emailUsuario = namedJdbcTemplate.queryForObject(sql, params, String.class );

        //Cria o registro em tabel do token gerado

        String sql2 = "INSERT INTO ALISSON_DB.USUARIO (CODUSUARIO, TOKEN, DATATOKEN) VALUES (:token, :datatoken, :codUsuario)";
        SqlParameterSource parametro = new MapSqlParameterSource()
                .addValue("token", geraToken.getToken())
                .addValue("datatoken", geraToken.getExpiraToken())
                .addValue("codUsuario", codUsuario);
         namedJdbcTemplate.update(sql2, parametro);

        //Envia o email para o usuário
        log.info("Enviando email");
        var mensagem = new SimpleMailMessage();
        mensagem.setTo(emailUsuario);
        mensagem.setSubject(titulo);
        mensagem.setText("Para redefinir a sua senha clique no link: " + link);
        //javaMailSender.send(mensagem);
        log.info("Email enviado");

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

         String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE EMAIL = ? AND SENHA = ?) THEN 1 ELSE 0 END AS LOGIN_APROVADO FROM DUAL \n";
         String loginSucess = jdbcTemplate.queryForObject(sql, String.class, entityUser.getEmail(), entityUser.getSenha());

        if (loginSucess.equals("1")){
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

            String sql = "SELECT CASE WHEN EXISTS (SELECT 1 FROM ALISSON_DB.USUARIO WHERE ID = ? AND (SENHA != ? AND SENHA2 != ?)) THEN 1 ELSE 0 END AS SENHA_VALIDA FROM DUAL";
            String retornoConsulta = jdbcTemplate.queryForObject(sql, String.class, usuario.getId(), usuario.getSenha(), usuario.getSenha());

            if (retornoConsulta.equals("1")){
                response.setSucesso(true);
                response.setMensagem("Atualização realizada com sucesso. ");
            }else{
                response.setSucesso(false);
                response.setMensagem("Erro. A senha ja foi utilizada. ");
            }
        try {
            repository.atualizaUsuario(usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
