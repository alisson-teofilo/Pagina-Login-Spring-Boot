package com.project.demo.model;
import com.project.demo.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Vagas {

    private String ROWID;
    private String idVaga;
    private String cnpjEmpresa;
    private String titulo;
    private String descricao;
    private Date dataPublicacao;
    private int valorMensal;
    private String localAtuacao;
    private String url;
    private String companyName;
    private String companyLogo;
    private String jobLevel;

    public Vagas(Map<String, String> map) throws ParseException {
        this.ROWID = map.get("ROWID");
        this.idVaga = map.get("idVaga");
        this.url = map.get("url");
        this.titulo = map.get("jobTitle");
        this.companyName = map.get("companyName");
        this.companyLogo = map.get("companyLogo");
        this.localAtuacao = map.get("jobGeo");
        this.descricao = map.get("jobDescription");
        this.jobLevel = map.get("jobLevel");
        this.dataPublicacao = DataUtil.dateConvertApi(map.get("pubDate"));
    }

}
