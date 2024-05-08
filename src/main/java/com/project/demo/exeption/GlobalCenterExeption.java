package com.project.demo.exeption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalCenterExeption {

    @ExceptionHandler(RegrasNegocioException.class)
    public ResponseEntity<?> regrasNegocioException(RegrasNegocioException ex) {

        log.error("Regra de negócio não atendida: " +  ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalException(Exception ex) {

        log.error("Erro inesperado: " + ex.getMessage());
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro inesperado!");
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<?> mailException(MailException ex) {

        log.error("Erro ao enviar email");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar email");
    }
}
