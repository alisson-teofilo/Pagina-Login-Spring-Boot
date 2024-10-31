package com.project.demo.dto.requestDTO;

import lombok.Data;

import java.util.Date;

@Data
public class VagasRequestDTO {

    private String ROWID;
    private String cnpjEmpresa;
    private String titulo;
    private String descricao;
    private String companyName;
    private Date dataPublicacao;
    private int valorMensal;
    private String localAtuacao;
    private String  plataforma;
    private String idVaga;
    private String cpfUsuario;
    private String jobLevel;
}
