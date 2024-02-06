package com.project.demo.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    private String id;
    private String nome;
    private String senha;
    private String senha2;
    private String senha3;
    private String senha4;
    private String email;
    private String token;
}
