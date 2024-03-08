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
public class Usuario {

    @Id
    @Column (name ="CPF")
    private String cpf;

    @Column (name = "ID")
    private String id;

    @Column (name = "NOME")
    private String nome;

    @Column(name ="SENHA")
    private String senha;

    @Column (name = "EMAIL")
    private String email;


    private String token;

}
