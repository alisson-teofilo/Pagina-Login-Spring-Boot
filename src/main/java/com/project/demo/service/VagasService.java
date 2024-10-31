package com.project.demo.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.demo.dto.JobsDTO;
import com.project.demo.dto.requestDTO.VagasRequestDTO;
import com.project.demo.dto.responseDTO.VagasResponseDTO;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.model.Vagas;
import com.project.demo.repository.VagasRepository;
import com.project.demo.util.JsonFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.text.ParseException;
import java.util.*;



@Service
public class VagasService {

    private VagasRepository repository;
    private HttpResponse<String> apiResponse = null;

    @Autowired
    public VagasService(VagasRepository vagasRepository) {
        this.repository = vagasRepository;
    }

    public List<VagasResponseDTO> listarVagas() {
        List<Vagas> retorno = repository.listarVagas();
        if (retorno.isEmpty()) {
            throw new RegrasNegocioException("Erro ao listar vagas");
        }
        return VagasResponseDTO.convert(retorno);
    }

    public List<VagasResponseDTO> listarCandidaturasByUsuaio(String cpfUsuario) {
        List<Vagas> retorno = repository.listarCandidaturasByUsuaio(cpfUsuario);
        if (retorno.isEmpty()) {
            throw new RegrasNegocioException("Erro ao listar vagas");
        }
        return VagasResponseDTO.convert(retorno);
    }

    public List<Vagas> cadastrarVagas(VagasRequestDTO requestDTO) {
        repository.cadastrarVagas(requestDTO);
        return repository.vagasPublicadas(requestDTO.getCnpjEmpresa());
    }

    public List<VagasResponseDTO>  buscarVagasPublicas(String cpnj) {
        List<Vagas> response = repository.vagasPublicadas(cpnj);
        return VagasResponseDTO.convert(response);
    }

    public void candidatarVaga(VagasRequestDTO requestDTO) {

        repository.candidatarVaga(requestDTO);
    }

    public void deletarCandidatura(VagasRequestDTO requestDTO) {

        repository.deletarCandidatura(requestDTO);
    }

    public void editarVagas(VagasRequestDTO vagasReqeuest) {
        if(repository.editarVagas(vagasReqeuest) != 1 ) {
            throw new RuntimeException("Erro ao atualizar vaga");
        }
    }

    public List<VagasResponseDTO> buscarVagas(VagasRequestDTO request) throws IOException, ParseException {

        List<Vagas> listaVagasUnificadas = null;
        List<Vagas> listaVagasInternas = repository.buscarVagas(request);

        // Se não há descrição não chamamos a API externa
        if(request.getPlataforma().equals("Externo")) {

            if (apiResponse == null) {
                apiResponse = searchInApi(request.getDescricao());
            }

            List<Vagas> arrayApi = objectFormat(apiResponse.body());

            listaVagasUnificadas = listUnify(arrayApi, listaVagasInternas);
        }

        if(listaVagasInternas.isEmpty() && listaVagasUnificadas == null ){
            throw new RegrasNegocioException("NENHUMA VAGA ENCONTRADA");
        }

        return VagasResponseDTO.convert(listaVagasUnificadas != null ? listaVagasUnificadas : listaVagasInternas);
    }

    // Unifica os arrays de objetos
    private List<Vagas> listUnify(List<Vagas> arrayApi, List<Vagas> vagas ) throws ParseException {

        List<Vagas> listUnifyted = new ArrayList<>();
        listUnifyted.addAll(arrayApi);
        listUnifyted.addAll(vagas);

        return listUnifyted;
    }

    // Consome uma api para encontrar vagas
    private HttpResponse<String> searchInApi(String jobParamSearch) {
        try {

            String baseUrl = "https://jobicy.p.rapidapi.com/api/v2/remote-jobs?tag=";
            String localizacao = "&geo=brazil";
            String url = baseUrl + jobParamSearch + localizacao;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("X-RapidAPI-Key", "7fce903ae4mshf9400038e709182p131457jsnb548d1c3fd8f")
                    .header("X-RapidAPI-Host", "jobicy.p.rapidapi.com")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Formata a string em um novo objeto JobsDTO e cria nova lista de objetos
    private List<Vagas> objectFormat(String response) throws JsonProcessingException, ParseException {

        List<JobsDTO> jsonFormatado = JsonFormatter.formatJson(response);
        List<Vagas> listaVagasFiltrada = new ArrayList<>();

            for(JobsDTO job : jsonFormatado) {

                if(job.getJobGeo().equals("Anywhere")) {

                    Map<String, String> map = new HashMap<>();
                    map.put("id", Integer.toString(job.getId()));
                    map.put("url", job.getUrl());
                    map.put("jobTitle", job.getJobTitle());
                    map.put("companyName", job.getCompanyName());
                    map.put("companyLogo", job.getCompanyLogo());
                    map.put("jobGeo", job.getJobGeo());
                    map.put("jobLevel", job.getJobLevel());
                    map.put("jobDescription", job.getJobDescription());
                    map.put("pubDate", job.getPubDate());

                    Vagas vaga = new Vagas(map);

                    listaVagasFiltrada.add(vaga);
               }
            }
        return listaVagasFiltrada;
    }

}















