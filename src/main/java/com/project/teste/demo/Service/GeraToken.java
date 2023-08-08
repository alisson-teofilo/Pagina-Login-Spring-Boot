package com.project.teste.demo.Service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Data
public class GeraToken {

    private String token;
    private LocalDateTime expiraToken;

    // Construtor
    public GeraToken(){
        this.token = geraTokenString();
        this.expiraToken = LocalDateTime.now().plusMinutes(5);
    }

    // Gera o token
    public String geraTokenString(){
        return UUID.randomUUID().toString();
    }

    // Verifica a validade do Token
    public boolean ehTokenValido(LocalDateTime dadosTokenUsuario){
        return LocalDateTime.now().isAfter(dadosTokenUsuario);
    }

}
