package com.project.teste.demo.Controller;

import com.project.teste.demo.Dto.DtoResponse;
import com.project.teste.demo.Dto.UsuarioRespose;
import com.project.teste.demo.Exception.RegrasNegocioException;
import com.project.teste.demo.Model.Usuario;
import com.project.teste.demo.Service.GeraToken;
import com.project.teste.demo.Service.UsuarioService;
import lombok.Data;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Data

@RestController
@RequestMapping("/usuario")

@Slf4j
public class UsuarioController {

    Logger logger = LogManager.getLogger(UsuarioController.class);

    @Autowired
    UsuarioService service;

    @PostMapping("/validaToken")
    public ResponseEntity<?> validaToken(@RequestBody Usuario modelUsuario, GeraToken classtoken, UsuarioRespose response){
        logger.info("Validar Token");
            UsuarioRespose retorno;
        try {
            service.validaToken(modelUsuario, classtoken, response);
        } catch (DataAccessException | RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody Usuario modelUsuario, GeraToken classeToken, UsuarioRespose response){
        logger.info("Enviar Email");
        try {
            service.enviarEmail(modelUsuario, classeToken, response);
        } catch (MailException| DataAccessException | RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Usuario entityUser, UsuarioRespose loginResponse){
        logger.info("Logar Usuario");
        try {
            service.loginUserService(entityUser, loginResponse);
        }catch (RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> createUser(@RequestBody Usuario entityUser, UsuarioRespose respose){
        logger.info("Cadastrar Usuarios");
        try {
            service.createUserService(entityUser, respose);
        } catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/listaUsuarios")
    public ResponseEntity<List<DtoResponse>> listaUsuariosController() {
        logger.info("Listar Usuarios");
        List<Usuario> retornoLista = null;
        try {
            retornoLista = service.listaUsuarioService();
            List<DtoResponse> dtoResponseList = DtoResponse.convert(retornoLista);
            return ResponseEntity.ok(dtoResponseList);
        } catch (DataAccessException | RegrasNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(new DtoResponse(e.getMessage())));
        }
    }

    @PutMapping("/atualizaCadastro")
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario entityUsuario, UsuarioRespose response) {
        logger.info("Atualizar Usuários");
        try{
            service.atualizaUsuario(entityUsuario, response);
        } catch (DataAccessException | RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
