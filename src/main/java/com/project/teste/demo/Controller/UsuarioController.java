package com.project.teste.demo.Controller;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import com.project.teste.demo.Service.UsuarioService;
import lombok.Data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/usuario")

public class UsuarioController {

    Logger logger = LogManager.getLogger(UsuarioController.class);

    @Autowired
    UsuarioService service;

    @PostMapping("/validaToken")
    public ResponseEntity<?> validaToken(@RequestBody Usuario modelUsuario, GeraToken classtoken, UsuarioRespose response){
        UsuarioRespose retorno = service.validaToken(modelUsuario, classtoken, response);
        return new ResponseEntity<>(retorno,HttpStatus.OK);
    }

    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response){
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.fatal("FATAL");
       return service.enviarEmail(modelUsuario, classeToken, response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Usuario entityUser, UsuarioRespose loginResponse){
        UsuarioRespose retornoService = service.loginUserService(entityUser, loginResponse);
        return new ResponseEntity<>(retornoService,HttpStatus.OK);
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> createUser(@RequestBody Usuario entityUser, UsuarioRespose respose){
        UsuarioRespose retornoService = service.createUserService(entityUser, respose);
        return new ResponseEntity<>(retornoService, HttpStatus.OK);
    }

    @GetMapping("/listaUsuarios")
    public ResponseEntity<?> listaUsuariosController(UsuarioRespose response){
        List<Usuario> retornoLista = service.listaUsuarioService(response);
        return new ResponseEntity<>(retornoLista, HttpStatus.OK);

    }

    @PutMapping("/atualizaCadastro")
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario entityUsuario, UsuarioRespose response) {
        return service.atualizaUsuario(entityUsuario, response);
    }


}
