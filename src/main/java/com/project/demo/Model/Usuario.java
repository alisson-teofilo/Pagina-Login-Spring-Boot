package com.project.demo.Model;

import com.project.demo.Dto.UsuarioResponseDTO;
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
    @Column (name = "ID")
    private String id;
    @Column (name = "NOME")
    private String nome;
    @Column(name ="SENHA")
    private String senha;
    @Column (name ="SENHA2")
    private String senha2;
    @Column (name = "SENHA3")
    private String senha3;
    @Column (name = "SENHA4")
    private String senha4;
    @Column (name = "EMAIL")
    private String email;

    private String token;

    public UsuarioResponseDTO usuarioToDto(UsuarioResponseDTO usuarioResponseDTO){
        this.id = usuarioResponseDTO.getId();
        this.nome = usuarioResponseDTO.getNome();
        return usuarioResponseDTO;
    }

}
