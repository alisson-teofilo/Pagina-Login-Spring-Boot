package com.project.demo.controller;


import com.project.demo.dto.requestDTO.UsuarioPjRequest;
import com.project.demo.dto.responseDTO.UsuarioPjResponseDTO;
import com.project.demo.service.UsuarioPjService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping("link-vagas/empresa")
@Slf4j
public class UsuarioPjController {

    UsuarioPjService service;

    @Autowired
    public UsuarioPjController(UsuarioPjService service)
    {
        this.service = service;
    }

    @PostMapping("/cadastrarEmpresa")
    public ResponseEntity<?> createUser(@RequestBody UsuarioPjRequest request) throws ParseException {
        log.info("Cadastrar Empresa");

            service.cadastrarUsuarioPj(request);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário cadastrado com sucesso");
    }

    @PutMapping("/editarCadastroEmpresa")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioPjRequest usuarioPfRequest){
        System.out.println(usuarioPfRequest);
        log.info("Atualizar Empesa");

            service.atualizaUsuarioPj(usuarioPfRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario atualizado com sucesso");
    }

    @GetMapping("buscarEmpresa/{cnpj}")
    public ResponseEntity<?> buscarUsuario(@PathVariable String cnpj){
        log.info("Buscando usuário");

        UsuarioPjResponseDTO usuario = service.buscarUsuario(cnpj);

        return ResponseEntity.ok(usuario);
    }

}
