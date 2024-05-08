package com.project.demo.service;

import com.project.demo.dto.responseDTO.UsuarioResponseDTO;
import com.project.demo.model.Usuario;
import com.project.demo.dto.requestDTO.UsuarioRequestDTO;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class UsuarioService {

    private UsuarioRepository repository;

    @Autowired
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public void validaToken(UsuarioRequestDTO usuarioRequestDTO, GeraToken classeToken, UsuarioRequestDTO response) {

        String dataTokenUsuario = repository.tokenValidoRepository(usuarioRequestDTO);
        if(dataTokenUsuario.equals("0")){
            throw new RegrasNegocioException("Token não encontrado");
        }

        // converte a string em LocalDate
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate dataFormatada = LocalDate.parse(dataTokenUsuario, formataData);

        if(classeToken.ehTokenValido(dataFormatada)){
            throw new RegrasNegocioException("Token expirado");
        }
    }

    public void enviarEmail(UsuarioRequestDTO usuarioRequestDTO, GeraToken classeToken) throws MailException, DataAccessException, RegrasNegocioException {

            // URL usada para trocar a senha
            String baseUrl = "http://localhost:9000/cadastro";

             // valida ID
            String validId = repository.validaId(usuarioRequestDTO);
            if(validId.equals("0")){
                throw new RegrasNegocioException("Usuário não encontrado");
            }

             // Consulta o email do usuário logado
            String emailUsiario = repository.consultaEmail(usuarioRequestDTO);
            if (emailUsiario.equals("0")){
                throw new RegrasNegocioException ("Email não encontrado");
            }

            // Cria o registro em tabel do token gerado
            int insereDadosTabela = repository.insereTokenTabela(classeToken, usuarioRequestDTO);
            if (insereDadosTabela != 1){
                throw new RegrasNegocioException("Erro ao gerar token") {};
            }

            // Dispara Email
              repository.disparaEmail(baseUrl, emailUsiario, classeToken);
    }

    public void loginUserService(UsuarioRequestDTO usuarioRequestDTO)  {

        String returno = repository.validaId(usuarioRequestDTO);
        if (!returno.equals("1")){
            throw new RegrasNegocioException("Usuário não cadastrado") {};
        }

        int ehLoginValido = repository.validaLogin(usuarioRequestDTO);
        if (ehLoginValido != 1){
            throw new RegrasNegocioException("Credenciais inválidas") {};
        }

    }

    public void createUserService(UsuarioRequestDTO usuarioRequestDTO) {

       int retornoRepository = repository.crateUserRepository(usuarioRequestDTO);
       if (retornoRepository != 1){
           throw new DataAccessException("Erro ao cadastrar usuário") {};
       }

    }

    public List<UsuarioResponseDTO> listaUsuarioService() {

        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();
        if(retornoConsulta == null){
            throw new RegrasNegocioException("Usuário não encontrado"){};
        }

        return UsuarioResponseDTO.convert(retornoConsulta);
    }

    public void atualizaUsuario(UsuarioRequestDTO usuarioRequestDTO) {

            String retorno = repository.validaId(usuarioRequestDTO);
            if(retorno == null){
                throw new RegrasNegocioException("Usuário não encontrado");
            }

            int linhasAtualizadas = repository.atualizaUsuario(usuarioRequestDTO);
            if(linhasAtualizadas != 1){
                throw new RegrasNegocioException("Erro ao atualizar cadastro de usuário");
            }
    }

}
