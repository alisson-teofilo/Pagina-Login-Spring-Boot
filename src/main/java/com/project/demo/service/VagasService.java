package com.project.demo.service;

import com.project.demo.dto.responseDTO.VagasResponseDTO;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.model.Vagas;
import com.project.demo.repository.VagasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VagasService {

    private UsuarioService usuarioService;

    private VagasRepository repository;

    @Autowired
    public VagasService(VagasRepository vagasRepository){
        this.repository = vagasRepository;
    }

    public List<VagasResponseDTO> listarVagas()
    {
        List<Vagas> retorno = repository.listarVagas();
        if(retorno.isEmpty()){
            throw new RegrasNegocioException("Erro ao listar vagas");
        }
        return VagasResponseDTO.convert(retorno);
    }


}
