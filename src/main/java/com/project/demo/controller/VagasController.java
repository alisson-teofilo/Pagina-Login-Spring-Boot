package com.project.demo.controller;

import com.project.demo.dto.requestDTO.VagasRequestDTO;
import com.project.demo.dto.responseDTO.VagasResponseDTO;
import com.project.demo.service.VagasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vagas")
@Slf4j
public class VagasController {

  VagasService service;

  @Autowired
  public VagasController(VagasService vagasService){
    this.service = vagasService;
  }

  @GetMapping("/listarVagas")
    public ResponseEntity<?> listarVagas() {

      log.info("ENDPOINT / Listar vagas");

      List<VagasResponseDTO> retorno = service.listarVagas();
      return ResponseEntity.ok(retorno);
  }

  @PostMapping("/cadastrarVagas")
  public void insereVagas(@RequestBody VagasRequestDTO vagas) {
    log.info("ENDPOINT / Cadastrar vagas");

    service.inserirVaga(vagas);
  }
}
