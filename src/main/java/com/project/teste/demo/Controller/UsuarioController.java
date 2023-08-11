package com.project.teste.demo.Controller;

import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import com.project.teste.demo.Service.UsuarioService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/usuario")

public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/validaToken")
    public ResponseEntity<?> validaToken(@RequestBody Usuario modelUsuario, GeraToken classtoken, UsuarioRespose response){
        UsuarioRespose retorno = service.validaToken(modelUsuario, classtoken, response);
        return new ResponseEntity<>(retorno,HttpStatus.OK);
    }

    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response){
       UsuarioRespose retorno = service.enviarEmail(modelUsuario, classeToken, response);
        return new ResponseEntity<>(retorno, HttpStatus.OK);
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

    @PostMapping("/atualizaCadastro")
    public ResponseEntity<UsuarioRespose> updateUsuario(@RequestBody Usuario entityUsuario) {
        ResponseEntity<UsuarioRespose> responseEntity = service.atualizaUsuario(entityUsuario);
        return responseEntity;
    }



}
