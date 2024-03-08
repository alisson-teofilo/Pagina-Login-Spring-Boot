package com.project.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoPf {


    @Column(name ="CPF")
    private String cpf;

    @Id
    @Column(name ="RUA")
    private String rua;

    @Column(name ="numero")
    private int numero;

    @Column(name ="CIDADE")
    private String cidade;

    @Column(name ="ESTADO")
    private String estado;

    @Column(name ="PAIS")
    private String pais;

    @Column(name = "COMPLEMENTO")
    private String complemento;

}
