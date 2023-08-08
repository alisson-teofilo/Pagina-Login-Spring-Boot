package com.project.teste.demo.Controller;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
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

    @PostMapping("/validaToken/{token}/{codUsuario}")
    public ResponseEntity<?> validaToken(@PathVariable ("token") String token, @PathVariable ("codUsuario") String codUsuario){
        service.validaToken(token, codUsuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/enviaEmail/{codUsuario}")
    public ResponseEntity<?> enviaEmail(@PathVariable ("codUsuario") String codUsuario){
         service.enviarEmail("alissonteofilo@gmail.com", "Recuperação de Senha", codUsuario);
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
