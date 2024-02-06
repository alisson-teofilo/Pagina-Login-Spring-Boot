package com.project.demo.Controller;

import com.project.demo.Dto.UsuarioResponseDTO;
import com.project.demo.Dto.UsuarioRequestDTO;
import com.project.demo.Exception.RegrasNegocioException;
import com.project.demo.Model.Usuario;
import com.project.demo.Service.GeraToken;
import com.project.demo.Service.UsuarioService;
import lombok.Data;
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
    public ResponseEntity<?> validaToken(@RequestBody UsuarioRequestDTO usuarioRequestDTO, GeraToken classtoken, UsuarioRequestDTO response){
        logger.info("Validar Token");
            UsuarioRequestDTO retorno;
        try {
            service.validaToken(usuarioRequestDTO, classtoken, response);
        } catch (DataAccessException | RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody UsuarioRequestDTO usuarioRequestDTO, GeraToken classeToken){
        logger.info("Enviar Email");
        try {
            service.enviarEmail(usuarioRequestDTO, classeToken);
        } catch (MailException| DataAccessException | RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body("As instruções foram enviadas no seu Email.");
    }
    @PostMapping("/efetuarLogin")
    public ResponseEntity<?> loginUser(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        logger.info("Logar Usuario");
        try {
            service.loginUserService(usuarioRequestDTO);
        }catch (RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/cadastrarUsuario")
    public ResponseEntity<?> createUser(@RequestBody UsuarioRequestDTO usuarioRequestDTO){
        logger.info("Cadastrar Usuarios");
        try {
            service.createUserService(usuarioRequestDTO);
        } catch (DataAccessException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/listaUsuarios")
    public ResponseEntity<List<?>> listaUsuariosController() {
        logger.info("Listar Usuarios");
        List<UsuarioResponseDTO> retornoLista = null;
        try {
            retornoLista = service.listaUsuarioService();
            return ResponseEntity.ok(retornoLista);
        } catch (DataAccessException | RegrasNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(new UsuarioResponseDTO(e.getMessage())));
        }
    }

    @PutMapping("/atualizaCadastro")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        logger.info("Atualizar Usuários");
        try{
            service.atualizaUsuario(usuarioRequestDTO);
        } catch (DataAccessException | RegrasNegocioException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
