package com.project.demo.service;

import com.project.demo.dto.requestDTO.LoginRequest;
import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.repository.LoginRepository;
import com.project.demo.repository.UsuarioPfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class LoginService {
    LoginRepository repository;

    UsuarioPfRepository userRepository;

    @Autowired
    public LoginService(LoginRepository repository, UsuarioPfRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public void validaToken(UsuarioPfRequest usuarioPfRequest, GeraToken classeToken, UsuarioPfRequest response) {

        String dataTokenUsuario = repository.tokenValidoRepository(usuarioPfRequest);
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

    public void enviarEmail(LoginRequest dto, GeraToken classeToken) {

        // URL usada para trocar a senha
        String baseUrl = "http://localhost:9000/cadastro";

        // valida ID
        String validId = repository.validaId(dto.getId());
        if(validId.equals("0")){
            throw new RegrasNegocioException("Usuário não encontrado");
        }

        // Consulta o email do usuário logado
        String emailUsiario = repository.consultaEmail(dto);
        if (emailUsiario.equals("0")){
            throw new RegrasNegocioException ("Email não encontrado");
        }

        // Cria o registro em tabel do token gerado
        int insereDadosTabela = repository.insereTokenTabela(classeToken, dto);
        if (insereDadosTabela != 1){
            throw new RegrasNegocioException("Erro ao gerar token") {};
        }

        // Dispara Email
        repository.disparaEmail(baseUrl, emailUsiario, classeToken);
    }

    public void loginUserService(LoginRequest dto)  {

        String returno = repository.validaId(dto.getId());
        if (returno.equals("0")){
            throw new RegrasNegocioException("ID inválido") {};
        }

        int ehLoginValido = repository.efeturaLogin(dto);
        if (ehLoginValido != 1){
            throw new RegrasNegocioException("Credenciais inválidas") {};
        }

    }

}
