package com.project.demo.service;

import com.project.demo.dto.requestDTO.LoginRequest;
import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.repository.LoginRepository;
import com.project.demo.repository.UsuarioPfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public void validaToken(LoginRequest request) {

        // valida se o token existe
        String dataTokenUsuario = repository.tokenValidoRepository(request);
        if(dataTokenUsuario.equals("0")){
            throw new RegrasNegocioException("Token não encontrado");
        }

        // converte a string em LocalDate
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss,SSSSSSSSS");
        System.out.println("dataTokenUsuario " + dataTokenUsuario);
        System.out.println("formataData " + formataData);
        LocalDateTime dataFormatada = LocalDateTime.parse(dataTokenUsuario, formataData);


        // valida se o token já expirou
        if(GeraToken.ehTokenValido(dataFormatada)){
            throw new RegrasNegocioException("Token expirado");
        }
    }

    public void enviarEmail(LoginRequest dto, GeraToken classeToken) {

        // URL usada para trocar a senha
        String baseUrl = "http://localhost:9000/recuperar-senha";

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
        repository.disparaEmail(baseUrl, emailUsiario, classeToken, dto);
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

    public void recuperarSenhaUsuario(LoginRequest dto)  {

        int returno = repository.recuperarSenhaUsuario(dto);
        if (returno == 0){
            throw new RuntimeException("Erro ao alterar senha") {};
        }

    }

    public void recuperarSenhaEmpresa(LoginRequest dto)  {

        int returno = repository.recuperarSenhaUsuario(dto);
        if (returno == 0){
            throw new RuntimeException("Erro ao alterar senha") {};
        }

    }

}
