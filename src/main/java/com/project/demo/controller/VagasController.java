package com.project.demo.controller;

import com.project.demo.dto.requestDTO.VagasRequestDTO;
import com.project.demo.dto.responseDTO.VagasResponseDTO;
import com.project.demo.model.Vagas;
import com.project.demo.service.VagasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("link-vagas/vagas")
@Slf4j
public class VagasController {

  VagasService service;

  @Autowired
  public VagasController(VagasService vagasService){
    this.service = vagasService;
  }

  @GetMapping("/listarVagas")
    public ResponseEntity<?> listarVagas() {
      log.info("LISTANDO VAGAS - /listarVagas");

      List<VagasResponseDTO> retorno = service.listarVagas();
      return ResponseEntity.ok(retorno);
  }

  @GetMapping("/listarCandidaturasByUsuaio/{cpfUsuario}")
    public ResponseEntity<?> listarCandidaturasByUsuaio(@PathVariable String cpfUsuario) {
      log.info(" LISTANDO VAGAS QUE O USUARIO APLICOU - /listarCandidaturasByUsuaio");

      List<VagasResponseDTO> retorno = service.listarCandidaturasByUsuaio(cpfUsuario);
      return ResponseEntity.ok(retorno);
  }

  @GetMapping("/vagasPublicadas/{cnpj}")
    public ResponseEntity<?> buscarVagasPublicas(@PathVariable String cnpj) {
      log.info("VAGAS PUBLICADAS PELA EMPRESA - /vagasPublicadas : " + cnpj);

    List<VagasResponseDTO> retorno = service.buscarVagasPublicas(cnpj);
      return ResponseEntity.ok(retorno);
  }

  @PostMapping("/cadastrarVagas")
  public ResponseEntity<?> cadastrarVagas(@RequestBody VagasRequestDTO vagas) {
    log.info("CADASTRANDO VAGA - /cadastrarVagas");

    List<Vagas> vagasPublicadas = service.cadastrarVagas(vagas);
    return ResponseEntity.ok(vagasPublicadas);
  }

  @PostMapping("/candidatarVaga")
  public void candidatarVaga(@RequestBody VagasRequestDTO vagas) {
    log.info("CANDIDATANDO A VAGA - /candidatarVaga");

    service.candidatarVaga(vagas);
  }

  @PutMapping("/deletarCandidatura")
  public void deletarCandidatura(@RequestBody VagasRequestDTO vagas) {
    log.info("EXCLUINDO CANDIDATURA - /deletarCandidatura");

    service.deletarCandidatura(vagas);
  }

  @PutMapping("/editarVagas")
  public void editarVagas(@RequestBody VagasRequestDTO vagas) {
    log.info("EDITANDO VAGA - /editarVagas");

    service.editarVagas(vagas);
  }

  @PostMapping("/buscarVagas")
  public ResponseEntity<?> buscarVagas(@RequestBody VagasRequestDTO request ) throws IOException, ParseException {
    log.info("BUSCANDO VAGAS /buscarVagas");

    List<VagasResponseDTO> vagasEncontradas = service.buscarVagas(request);
    return ResponseEntity.ok(vagasEncontradas);
  }

}
