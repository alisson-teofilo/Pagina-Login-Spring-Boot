package com.project.demo.dto.responseDTO;

import com.project.demo.model.UsuarioPF;
import com.project.demo.model.UsuarioPJ;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UsuarioPjResponseDTO {
    private String cnpj;
    private String nomeFantasia;
    private String email;

    public UsuarioPjResponseDTO(UsuarioPJ pj){
        this.cnpj = pj.getCnpj();
        this.nomeFantasia = pj.getNomeFantasia();
        this.email = pj.getEmail();
    }

    public static List<UsuarioPjResponseDTO> convert(List<UsuarioPJ> pj){
        return pj.stream().map(UsuarioPjResponseDTO::new).collect(Collectors.toList());
    }

    public static UsuarioPjResponseDTO convert(UsuarioPJ pj){
        return new UsuarioPjResponseDTO(pj);
    }
}
