package com.project.demo.dto.responseDTO;

import com.project.demo.model.UsuarioPF;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
@Data
public class UsuarioPfResponseDTO {
    private String id;
    private String nome;
    private String cpf;
    private String cargoAtual;
    private String email;

    public UsuarioPfResponseDTO(UsuarioPF UsuarioPF){
        this.id = UsuarioPF.getId();
        this.nome = UsuarioPF.getNome();
        this.cpf = UsuarioPF.getCpf();
        this.cargoAtual = UsuarioPF.getCargoAtual();
        this.email = UsuarioPF.getEmail();
    }

    public static List<UsuarioPfResponseDTO> convert(List<UsuarioPF> usuarioPF){
        return usuarioPF.stream().map(UsuarioPfResponseDTO::new).collect(Collectors.toList());
    }

    public static UsuarioPfResponseDTO convert(UsuarioPF usuarioPF) {
        return new UsuarioPfResponseDTO(usuarioPF);
    }

}