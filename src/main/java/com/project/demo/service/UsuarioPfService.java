package com.project.demo.service;

import com.project.demo.dto.responseDTO.UsuarioResponseDTO;
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

    private UsuarioPfRepository repository;
    private LoginRepository loginRepository;

    @Autowired
    public UsuarioPfService(UsuarioPfRepository repository, LoginRepository loginRepository) {
        this.repository = repository;
        this.loginRepository = loginRepository;
    }

    @Transactional
    public void createUserService(UsuarioPfRequest usuarioPfRequest) {

       int retornoRepository = repository.crateUserRepository(usuarioPfRequest);
       if (retornoRepository != 1){
           throw new DataAccessException("Erro ao cadastrar usuário") {};
       }

    }

    public List<UsuarioResponseDTO> listaUsuarioService() {

        List<Usuario> retornoConsulta = repository.listaUsuarioRepository();
        if(retornoConsulta == null){
            throw new RegrasNegocioException("Usuário não encontrado"){};
        }

        return UsuarioResponseDTO.convert(retornoConsulta);
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

}
