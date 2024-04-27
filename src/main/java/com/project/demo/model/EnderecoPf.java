package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoPf {

    private String cpf;
    private String rua;
    private int numero;
    private String cidade;
    private String estado;
    private String pais;
    private String complemento;

}
