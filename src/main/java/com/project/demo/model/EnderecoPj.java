package com.project.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoPj {

    private String cnpj;
    private String rua;
    private int numero;
    private String cidade;
    private String estado;
    private String pais;
    private String complemento;

}
