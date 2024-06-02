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
@RequestMapping("vagas")
@Slf4j
public class VagasController {

  VagasService service;

  @Autowired
  public VagasController(VagasService vagasService){
    this.service = vagasService;
  }

  @GetMapping("/listJobs")
    public ResponseEntity<?> listJobs() {
      log.info("ENDPOINT / JOB LIST");

      List<VagasResponseDTO> retorno = service.jobList();
      return ResponseEntity.ok(retorno);
  }

  @PostMapping("/jobCreator")
  public void jobCreator(@RequestBody VagasRequestDTO vagas) {
    log.info("ENDPOINT / JOB CREATE");

    service.jobInsert(vagas);
  }

  @PostMapping("/updateJobs")
  public void updateJobs(@RequestBody VagasRequestDTO vagas) {
    log.info("ENDPOINT /JOB UPDATE");

    service.updateJobs(vagas);
  }

  @GetMapping("/jobSearch/{jobParamSearch}")
  public ResponseEntity<?> jobSearch(@PathVariable String jobParamSearch ) throws IOException, InterruptedException, ParseException {
    log.info("ENDPOINT / JOB SEARCH");

    List<VagasResponseDTO> vagasEncontradas = service.searchJobs(jobParamSearch);
    return ResponseEntity.ok(vagasEncontradas);
  }

}
