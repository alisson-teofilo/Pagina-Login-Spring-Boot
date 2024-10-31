package com.project.demo.dto.requestDTO;

import lombok.Data;

@Data
public class LoginRequest {

    private String id;
    private String cnpj;
    private String senha;
    private String token;

}
