package com.project.demo.dto.responseDTO;

import com.project.demo.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Data
@NoArgsConstructor
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

    public static List<UsuarioResponseDTO> convert(List<Usuario> Usuario){
        return Usuario.stream().map(UsuarioResponseDTO::new).collect(Collectors.toList());
    }
}