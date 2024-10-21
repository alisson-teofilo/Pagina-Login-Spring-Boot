package com.project.demo.dto.responseDTO;

import com.project.demo.model.Usuario;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
@Data
public class UsuarioPfResponseDTO {
    private String id;
    private String nome;
    private String senha;

    public UsuarioPfResponseDTO(Usuario Usuario){
        this.id = Usuario.getId();
        this.nome = Usuario.getNome();
    }

    public UsuarioPfResponseDTO(String message) {
    }

    public static List<UsuarioPfResponseDTO> convert(List<Usuario> usuario){
        return usuario.stream().map(UsuarioPfResponseDTO::new).collect(Collectors.toList());
    }
}