package com.project.demo.service;

import com.project.demo.dto.responseDTO.UsuarioPfResponseDTO;
import com.project.demo.model.Usuario;
import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.repository.LoginRepository;
import com.project.demo.repository.UsuarioPfRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UsuarioPfService {

    private final UsuarioPfRepository repository;
    private final LoginRepository loginRepository;

    @Autowired
    public UsuarioPfService(UsuarioPfRepository repository, LoginRepository loginRepository) {
        this.repository = repository;
        this.loginRepository = loginRepository;
    }

    @Transactional
    public void cadastrarUsuario(UsuarioPfRequest usuarioPfRequest) {

       int retornoRepository = repository.cadastrarUsuario(usuarioPfRequest);

       if (retornoRepository != 1){
           throw new DataAccessException("Erro ao cadastrar usuário") {};
       }

    }

    public List<UsuarioPfResponseDTO> listarUsuario() {

        List<Usuario> retornoConsulta = repository.listarUsuario();
        if(retornoConsulta == null){
            throw new RegrasNegocioException("Usuário não encontrado"){};
        }

        return UsuarioPfResponseDTO.convert(retornoConsulta);
    }

    public void atualizaUsuario(UsuarioPfRequest usuarioPfRequest) {

        String retorno = loginRepository.validaId(usuarioPfRequest.getId());
        if(retorno == null){
            throw new RegrasNegocioException("Usuário não encontrado");
        }

        int linhasAtualizadas = repository.atualizaUsuario(usuarioPfRequest);
        if(linhasAtualizadas != 1){
           throw new RegrasNegocioException("Erro ao atualizar cadastro de usuário");
        }
    }

    public void excluirUsuario(UsuarioPfRequest request) {

        String retorno = loginRepository.validaId(request.getId());
        if(retorno == null){
            throw new RegrasNegocioException("Usuário não encontrado");
        }

        repository.excluirUsuario(request);
    }

    public void buscarUsuarioPF(UsuarioPfRequest request) {

        repository.excluirUsuario(request);
    }
}
