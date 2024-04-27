package com.project.demo.dto.responseDTO;


import com.project.demo.model.Vagas;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class VagasResponseDTO {
    private String cnpjEmpresa;
    private String titulo;
    private String descricao;
    private Date dataPublicacao;
    private int valorMensal;
    private String localAtuacao;

    public VagasResponseDTO(Vagas vagas){
        this.cnpjEmpresa = vagas.getCnpjEmpresa();
        this.titulo = vagas.getTitulo();
        this.descricao = vagas.getDescricao();
        this.dataPublicacao = vagas.getDataPublicacao();
        this.valorMensal = vagas.getValorMensal();
        this.localAtuacao = vagas.getLocalAtuacao();
    }
    public static List<VagasResponseDTO> convert(List<Vagas> vagas){
        return vagas.stream().map(VagasResponseDTO::new).collect(Collectors.toList());
    }


}
