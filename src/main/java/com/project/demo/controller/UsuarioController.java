package com.project.demo.controller;


import com.project.demo.dto.requestDTO.UsuarioRequestDTO;
import com.project.demo.dto.responseDTO.UsuarioResponseDTO;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.service.GeraToken;
import com.project.demo.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/usuario")
@Slf4j
public class UsuarioController {

    UsuarioService service;

    @Autowired
    public UsuarioController(UsuarioService service)
    {
        this.service = service;
    }

    @PostMapping("/validaToken")
    public ResponseEntity<?> validaToken(@RequestBody UsuarioRequestDTO usuarioRequestDTO, GeraToken classtoken, UsuarioRequestDTO response)
    {
        log.info("Validar Token");

            service.validaToken(usuarioRequestDTO, classtoken, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody UsuarioRequestDTO usuarioRequestDTO, GeraToken classeToken)
    {
        log.info("Enviar Email");

            service.enviarEmail(usuarioRequestDTO, classeToken);

        return ResponseEntity.status(HttpStatus.OK).body("Instruções foram enviadas no seu Email.");
    }

    @PostMapping("/efetuarLogin")
    public ResponseEntity<?> loginUser(@RequestBody UsuarioRequestDTO usuarioRequestDTO)
    {
        log.info("Logar Usuario");

            service.loginUserService(usuarioRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Autorizado com sucesso!");
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> createUser(@RequestBody UsuarioRequestDTO usuarioRequestDTO)
    {
        log.info("Cadastrar Usuarios");

            service.createUserService(usuarioRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/listaUsuarios")
    public ResponseEntity<List<?>> listaUsuariosController()
    {
        log.info("Listar Usuarios");

        List<UsuarioResponseDTO> retornoLista = service.listaUsuarioService();
            return ResponseEntity.ok(retornoLista);

    }

    @PutMapping("/atualizaCadastro")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO)
    {
        log.info("Atualizar Usuários");

            service.atualizaUsuario(usuarioRequestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
