package com.project.teste.demo.Dto;

import com.project.teste.demo.Model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class DtoResponse {
    private String id;
    private String nome;
    private String senha;

    public DtoResponse(Usuario usuario){
        this.id = usuario.getId();
        this.nome = usuario.getNome();
    }

    public DtoResponse(String message) {
    }

    public static List<DtoResponse> convert(List<Usuario> usuario){
        return usuario.stream().map(DtoResponse::new).collect(Collectors.toList());
    }
}
