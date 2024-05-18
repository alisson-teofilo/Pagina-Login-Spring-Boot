package com.project.demo.service;

import com.project.demo.dto.requestDTO.UsuarioPjRequest;
import com.project.demo.repository.UsuarioPjRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

@Service
public class UsuarioPjService {

    UsuarioPjRepository repository;

    public UsuarioPjService(UsuarioPjRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void cadastrarUsuarioPj(UsuarioPjRequest request) throws ParseException {
        repository.crateUserRepository(request);
    }

    public void atualizaUsuarioPj(UsuarioPjRequest request) {
        repository.updateUserPj(request);
    }
}
