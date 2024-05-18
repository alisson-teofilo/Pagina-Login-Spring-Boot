package com.project.demo.dto.requestDTO;

import lombok.Data;

@Data
public class UsuarioPjRequest {

    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private String fundacao;
    private String email;
    private String segmento;
    private int numeroFuncionario;
    private String senha;
}
