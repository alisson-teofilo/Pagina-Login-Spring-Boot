package com.project.demo.Service;

import com.project.demo.Dto.UsuarioResponseDTO;
import com.project.demo.Model.Usuario;
import com.project.demo.Dto.UsuarioRequestDTO;
import com.project.demo.Exception.RegrasNegocioException;
import com.project.demo.Repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public void validaToken(UsuarioRequestDTO usuarioRequestDTO, GeraToken classeToken, UsuarioRequestDTO response) throws DataAccessException, RegrasNegocioException {
        String dataTokenUsuario = repository.tokenValidoRepository(usuarioRequestDTO);
        // converte a string em LocalDate
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate dataFormatada = LocalDate.parse(dataTokenUsuario, formataData);
        
        LocalDate dataHoje = null;
        dataHoje = LocalDate.now();

        if(classeToken.ehTokenValido(dataFormatada)){
            throw new RegrasNegocioException("Token expirado");
        }
    }

    public void enviarEmail(UsuarioRequestDTO usuarioRequestDTO, GeraToken classeToken) throws MailException, DataAccessException, RegrasNegocioException {

            // URL usada para trocar a senha
            String baseUrl = "http://localhost:9000/cadastro";

             // valida ID
            String validaId = repository.validaId(usuarioRequestDTO);
            System.out.println(validaId);
            if(validaId == null || validaId.isEmpty()){
                throw new RegrasNegocioException("Usuário não encontrado");
            }

             // Consulta o email do usuário logado
            String emailUsiario = repository.consultaEmail(usuarioRequestDTO);

            if (emailUsiario == null ||emailUsiario.isEmpty()){
                throw new RegrasNegocioException ("Email não encontrado");
            }
            // Cria o registro em tabel do token gerado
            int insereDadosTabela = repository.insereTokenTabela(classeToken, usuarioRequestDTO);
            if (insereDadosTabela != 1){
                throw new DataAccessException("Erro ao gerar token") {};
            }
            // Dispara Email
              repository.disparaEmail(baseUrl, emailUsiario, classeToken);
    }

    public void loginUserService(UsuarioRequestDTO usuarioRequestDTO) throws RegrasNegocioException {

        String validaRE = repository.validaRe(usuarioRequestDTO);
        if (!validaRE.equals("1")){
            throw new RegrasNegocioException("Usuário não cadastrado") {};
        }

        String ehLoginValido = repository.validaLogin(usuarioRequestDTO);
        if (!ehLoginValido.equals("1")){
            throw new RegrasNegocioException("Credenciais inválidas") {};
        }

    }


    public void createUserService(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException{
        int retornoRepository = repository.crateUserRepository(usuarioRequestDTO);
        if (retornoRepository != 1){
            throw new DataAccessException("Erro ao cadastrar usuário") {};
        }
    }

    public List<UsuarioResponseDTO> listaUsuarioService() throws DataAccessException, RegrasNegocioException {

        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();

        if(retornoConsulta.isEmpty()){
            throw new RegrasNegocioException("Erro ao listar usuários"){};
        }
        return UsuarioResponseDTO.convert(retornoConsulta);
    }

    public void atualizaUsuario(UsuarioRequestDTO usuarioRequestDTO) throws DataAccessException, RegrasNegocioException {
            // valida ID
            String validaId = repository.validaId(usuarioRequestDTO);
            if(validaId == null || validaId.isEmpty()){
                throw new RegrasNegocioException("Usuário não encontrado");
            }

            // Valida Senha
            String validaSenhas = repository.validaSenhas(usuarioRequestDTO);
            if (!validaSenhas.equals("1")){
                throw new RegrasNegocioException("Erro. A senha ja foi utilizada. ");
            }
            // Atualzia usuário
            repository.atualizaUsuario(usuarioRequestDTO);
    }

}
