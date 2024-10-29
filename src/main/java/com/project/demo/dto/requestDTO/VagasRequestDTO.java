package com.project.demo.dto.requestDTO;

import lombok.Data;

import java.util.Date;

@Data
public class VagasRequestDTO {

    private String cnpjEmpresa;
    private String titulo;
    private String descricao;
    private Date dataPublicacao;
    private int valorMensal;
    private String localAtuacao;
    private String  plataforma;
}
