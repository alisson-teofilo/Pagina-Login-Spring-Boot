package com.project.demo.Model.mysql;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MenuMysql {
    @Id
    @Column (name = "COD_MODULO")
    private String codModulo;
    @Column (name = "COD_MODULO_PAI")
    private String codModuloPai;
    @Column (name = "DSC_FUNCAO")
    private String dscFuncao;

}
