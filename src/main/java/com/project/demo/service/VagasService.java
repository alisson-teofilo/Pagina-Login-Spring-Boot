package com.project.demo.service;

import com.project.demo.dto.responseDTO.VagasResponseDTO;
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
    public VagasService(UsuarioService service, VagasRepository vagasRepository){
        this.usuarioService = service;
        this.repository = vagasRepository;
    }

    public List<VagasResponseDTO> listarVagas(){
        List<Vagas> retorno = repository.listarVagas();
        return VagasResponseDTO.convert(retorno);
    }

    public boolean returTrue(){
        return usuarioService.retornoTrue();
    }


}
