package com.project.teste.demo.Service;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Exception.RegrasNegocioException;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.MailException;
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

    public void validaToken(Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response) throws DataAccessException, RegrasNegocioException {
        String dataTokenUsuario = repository.tokenValidoRepository(modelUsuario);
        // converte a string em LocalDate
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate dataFormatada = LocalDate.parse(dataTokenUsuario, formataData);
        
        LocalDate dataHoje = null;
        dataHoje = LocalDate.now();

        if(classeToken.ehTokenValido(dataFormatada)){
            throw new RegrasNegocioException("Token expirado");
        }
    }

    public ResponseEntity<?> enviarEmail(Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response) throws MailException, DataAccessException, RegrasNegocioException {
            // URL usada para trocar a senha
            String baseUrl = "http://localhost:9000/cadastro";

            // Consulta o email do usuário logado
            String emailUsiario = repository.consultaEmail(modelUsuario);
             System.out.println(emailUsiario);
            if (emailUsiario == null ||emailUsiario.isEmpty()){
                throw new RegrasNegocioException ("Email não encontrado");
            }
            // Cria o registro em tabel do token gerado
            int insereDadosTabela = repository.insereTokenTabela(classeToken, modelUsuario);
            if (insereDadosTabela != 1){
                throw new DataAccessException("Erro ao gerar token") {};
            }
            // Dispara Email
              repository.disparaEmail(baseUrl, emailUsiario, classeToken);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public UsuarioRespose loginUserService(Usuario entityUser, UsuarioRespose loginResponse) throws RegrasNegocioException {
        String retornoLogin = repository.validaLogin(entityUser);
        if (!retornoLogin.equals("1")){
            throw new RegrasNegocioException("Credenciais inválidas") {};
        }
        return loginResponse;
    }


    public void createUserService(Usuario entityUser, UsuarioRespose response) throws DataAccessException{
        int retornoRepository = repository.crateUserRepository(entityUser);
        if (retornoRepository != 1){
            throw new DataAccessException("Erro ao cadastrar usuário") {};
        }
    }

    public List<Usuario> listaUsuarioService(UsuarioRespose response) throws DataAccessException, RegrasNegocioException {
        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();
        if(retornoConsulta.isEmpty()){
            throw new RegrasNegocioException("Erro ao listar usuários"){};
        }
        return retornoConsulta;
    }

    public void atualizaUsuario(Usuario usuario, UsuarioRespose response) throws DataAccessException, RegrasNegocioException {
            // valida ID
            String validaId = repository.validaId(usuario);
            if(validaId == null || validaId.isEmpty()){
                throw new RegrasNegocioException("Usuário não encontrado");
            }

            // Valida Senha
            String validaSenhas = repository.validaSenhas(usuario);
            if (!validaSenhas.equals("1")){
                throw new RegrasNegocioException("Erro. A senha ja foi utilizada. ");
            }
            // Atualzia usuário
            repository.atualizaUsuario(usuario);
    }

}
