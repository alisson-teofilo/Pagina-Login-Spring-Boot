package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPJ {

    private String cnpj;
    private String id;
    private String nomeFantasia;
    private String senha;
    private String email;
    private String token;

}
