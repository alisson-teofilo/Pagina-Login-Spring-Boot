package com.project.demo.controller;


import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.dto.responseDTO.UsuarioResponseDTO;
import com.project.demo.service.UsuarioPfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/usuario")
@Slf4j
public class UsuarioPfController {

    UsuarioPfService service;

    @Autowired
    public UsuarioPfController(UsuarioPfService service)
    {
        this.service = service;
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> createUser(@RequestBody UsuarioPfRequest usuarioPfRequest)
    {
        log.info("Cadastrar Usuarios");

            service.createUserService(usuarioPfRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Usuário cadastrado com sucesso");
    }

    @GetMapping("/listaUsuarios")
    public ResponseEntity<List<?>> listaUsuariosController()
    {
        log.info("Listar Usuarios");

        List<UsuarioResponseDTO> retornoLista = service.listaUsuarioService();
            return ResponseEntity.ok(retornoLista);

    }

    @PutMapping("/atualizaCadastro")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioPfRequest usuarioPfRequest)
    {
        log.info("Atualizar Usuários");

            service.atualizaUsuario(usuarioPfRequest);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario atualizado com sucesso");
    }


}
