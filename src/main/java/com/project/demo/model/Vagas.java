package com.project.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Vagas {


    @Column(name = "CNPJ_EMPRESA")
    private String cnpjEmpresa;

    @Column(name = "TITULO")
    private String titulo;
    @Id
    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "DATA_PUBLICACAO")
    private Date dataPublicacao;

    @Column(name = "VALOR_MENSAL")
    private int valorMensal;

    @Column(name = "LOCAL_ATUACAO")
    private String localAtuacao;

}
