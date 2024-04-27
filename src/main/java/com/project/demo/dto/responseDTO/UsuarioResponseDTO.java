package com.project.demo.dto.responseDTO;

import com.project.demo.model.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
@Data
public class UsuarioResponseDTO {
    private String id;
    private String nome;
    private String senha;

    public UsuarioResponseDTO(Usuario Usuario){
        this.id = Usuario.getId();
        this.nome = Usuario.getNome();
    }

    public UsuarioResponseDTO(String message) {
    }

    public static List<UsuarioResponseDTO> convert(List<Usuario> usuario){
        return usuario.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }
}