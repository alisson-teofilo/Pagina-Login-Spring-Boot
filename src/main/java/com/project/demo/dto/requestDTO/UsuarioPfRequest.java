package com.project.demo.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPfRequest {

    private String id;
    private String nome;
    private String senha;
    private String email;
    private String token;
    private String cpf;
}