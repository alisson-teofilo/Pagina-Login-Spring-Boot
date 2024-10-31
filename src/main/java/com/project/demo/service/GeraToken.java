package com.project.demo.service;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Data

/**
 *  MÉTODOS PRECISAM SER STATIC, EVITANDO PASSAR TOKEN POR PARÂMETRO
 */

public class GeraToken {

    private String token;
    private LocalDateTime dataToken;

    // Construtor
    public GeraToken(){
        this.token = geraTokenString();
        this.dataToken = LocalDateTime.now().plusDays(1);
    }

    // Gera o token
    public String geraTokenString(){
        return UUID.randomUUID().toString();
    }

    // Verifica a validade do Token
    public static boolean ehTokenValido(LocalDateTime dadosTokenUsuario){
        return LocalDateTime.now().isAfter(dadosTokenUsuario);
    }

}
