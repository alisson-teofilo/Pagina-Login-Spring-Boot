package com.project.teste.demo.Service;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public UsuarioService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public UsuarioRespose validaToken(Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response) {

        String dataTokenUsuario = repository.tokenValidoRepository(modelUsuario);

        // converte a string em LocalDate
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate dataFormatada = LocalDate.parse(dataTokenUsuario, formataData);
        
        LocalDate dataHoje = null;
        dataHoje = LocalDate.now();

        if(!classeToken.ehTokenValido(dataFormatada)){
            response.setSucesso(true);
            response.setMensagem("Validado com sucesso!");
        } else {
            response.setSucesso(false);
            response.setMensagem("Token expirado");
        }
        return response;
    }

    public UsuarioRespose enviarEmail(Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response){
            // URL usada para trocar a senha
            String baseUrl = "http://localhost:9000/cadastro";

            // Consulta o email do usuário logado
            String emailUsiario = repository.consultaEmail(modelUsuario);
            if (emailUsiario.isEmpty()){
                response.setSucesso(false);
                response.setMensagem("Email não encontrado");
                return response;
            }else{
                response.setSucesso(true);
                response.setMensagem("Link de recuperação enviado no Email");
            }
            // Cria o registro em tabel do token gerado
            int insereDadosTabela = repository.insereTokenTabela(classeToken, modelUsuario);
            if (insereDadosTabela != 1){
                response.setSucesso(false);
                response.setMensagem("Falha ao gerar token");
                return response;
            }
            // Dispara Email
              repository.disparaEmail(baseUrl, emailUsiario, classeToken);

        return response;
    }


    public UsuarioRespose createUserService(Usuario entityUser, UsuarioRespose response) {
        int retornoRepository = repository.crateUserRepository(entityUser);
        if (retornoRepository != 1){
            response.setSucesso(false);
            response.setMensagem("Erro ao cadastrar usupario");
            return response;
        } else {
            response.setSucesso(true);
            response.setMensagem("Usuário cadastrado");
        }
        return response;
    }

    public List<Usuario> listaUsuarioService(UsuarioRespose response) {
        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();
        if(retornoConsulta.isEmpty()){
            response.setSucesso(false);
            response.setMensagem("Erro ao listar usuários");
        }
        return retornoConsulta;
    }

    public UsuarioRespose loginUserService(Usuario entityUser, UsuarioRespose loginResponse) {

        String retornoLogin = repository.validaLogin(entityUser);

        if (retornoLogin.equals("1")){
            loginResponse.setSucesso(true);
            loginResponse.setMensagem("Login efetuado com sucesso");
        } else{
            loginResponse.setSucesso(false);
            loginResponse.setMensagem("Credenciais inválidas");
        }

        return loginResponse;
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
