package com.project.teste.demo.Controller;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import com.project.teste.demo.Service.UsuarioService;
import lombok.Data;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@Data
@RestController
@RequestMapping("/usuario")

public class UsuarioController {
    @Autowired
    UsuarioService service;

    @PostMapping("/validaToken/{token}")
    public ResponseEntity<?> validaToken(@PathVariable ("token") String token, Usuario modelUsuario, GeraToken classtoken){
        service.validaToken(token, modelUsuario, classtoken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody Usuario modelUsuario, GeraToken classeToken){
         service.enviarEmail(modelUsuario, classeToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioRespose> loginUser(@RequestBody Usuario entityUser){
        ResponseEntity<UsuarioRespose> retornoService = service.loginUserService(entityUser);
        return retornoService;
    }

    @PostMapping("/cadastrarUsuario")
    public int createUser(@RequestBody Usuario entityUser){
        int retornoService = service.createUserService(entityUser);
        return retornoService;
    }

    @GetMapping("/listaUsuarios")
    public ResponseEntity<?> listaUsuariosController(){
        List<Usuario>  retornoLista = service.listaUsuarioService();
        return new ResponseEntity<>(retornoLista, HttpStatus.OK);

    }

    @PostMapping("/atualizaCadastro")
    public ResponseEntity<UsuarioRespose> updateUsuario(@RequestBody Usuario entityUsuario) {

        ResponseEntity<UsuarioRespose> responseEntity = service.atualizaUsuario(entityUsuario);
        return responseEntity;
    }



}
