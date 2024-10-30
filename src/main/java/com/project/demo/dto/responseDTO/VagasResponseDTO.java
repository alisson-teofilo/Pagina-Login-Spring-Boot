package com.project.demo.dto.responseDTO;


import com.project.demo.model.Vagas;
import com.project.demo.util.DataUtil;
import lombok.Data;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class VagasResponseDTO {
    private String ROWID;
    private String idVaga;
    private String titulo;
    private String descricao;
    private Date dataPublicacao;
    private int valorMensal;
    private String localAtuacao;
    private String url;
    private String companyName;
    private String companyLogo;
    private String jobLevel;

    public VagasResponseDTO(Vagas vagas){
        this.ROWID = vagas.getROWID();
        this.idVaga = vagas.getIdVaga();
        this.companyName = vagas.getCompanyName();
        this.companyLogo = vagas.getCompanyLogo();
        this.url = vagas.getUrl();
        this.titulo = vagas.getTitulo();
        this.descricao = vagas.getDescricao();
        this.dataPublicacao = vagas.getDataPublicacao();
        this.valorMensal = vagas.getValorMensal();
        this.localAtuacao = vagas.getLocalAtuacao();
    }

    public static List<VagasResponseDTO> convert(List<Vagas> vagas) {
        return vagas.stream().map(VagasResponseDTO::new).collect(Collectors.toList());
    }

}
