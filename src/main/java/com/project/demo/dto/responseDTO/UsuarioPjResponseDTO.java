package com.project.demo.dto.responseDTO;

import com.project.demo.model.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioPjResponseDTO {
    private String id;
    private String nome;
    private String senha;

    public UsuarioPjResponseDTO(Usuario Usuario){
        this.id = Usuario.getId();
        this.nome = Usuario.getNome();
    }

    public UsuarioPjResponseDTO(String message) {
    }

    public static List<UsuarioPjResponseDTO> convert(List<Usuario> usuario){
        return usuario.stream().map(UsuarioPjResponseDTO::new).collect(Collectors.toList());
    }
}