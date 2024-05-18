package com.project.demo.controller;

import com.project.demo.dto.requestDTO.LoginRequest;
import com.project.demo.dto.requestDTO.UsuarioPfRequest;
import com.project.demo.service.GeraToken;
import com.project.demo.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("login")
public class LoginController {

    LoginService loginService;

    public LoginController(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @PostMapping("/validaToken")
    public ResponseEntity<?> validaToken(@RequestBody UsuarioPfRequest usuarioPfRequest, GeraToken classtoken, UsuarioPfRequest response)
    {
        log.info("Validar Token");

        loginService.validaToken(usuarioPfRequest, classtoken, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/enviaEmail")
    public ResponseEntity<?> enviaEmail(@RequestBody LoginRequest dto, GeraToken classeToken)
    {
        log.info("Enviar Email");

        loginService.enviarEmail(dto, classeToken);

        return ResponseEntity.status(HttpStatus.OK).body("Instruções foram enviadas no seu Email.");
    }

    @PostMapping("/efetuarLogin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest dto)
    {
        log.info("Logando");

        loginService.loginUserService(dto);

        return ResponseEntity.status(HttpStatus.OK).body("Login Autorizado!");
    }

}
