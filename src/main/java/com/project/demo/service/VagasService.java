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

    public void cadastrarVagas(VagasRequestDTO requestDTO) {
        repository.cadastrarVagas(requestDTO);
    }

    public void editarVagas(VagasRequestDTO vagasReqeuest) {
        int registroAtualizado = repository.editarVagas(vagasReqeuest);
    }

    public List<VagasResponseDTO> buscarVagas(String jobParamSearch) throws IOException, ParseException {
        List<Vagas> jobs = repository.buscarVagas(jobParamSearch);

        HttpResponse<String> response = searchInApi(jobParamSearch);

        List<Vagas> arrayApi = objectFormat(response.body());

        List<Vagas> newArray = listUnify(arrayApi, jobs);

        return VagasResponseDTO.convert(newArray);
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

        List<JobsDTO> novaLista = JsonFormatter.formatJson(response);
        List<Vagas> arrayWithNeweo = new ArrayList<>();

            for(JobsDTO job : novaLista) {

                if(job.getJobGeo().equals("Anywhere")) {
                    Map<String, String> map = new HashMap<>();
                    map.put("url", job.getUrl());
                    map.put("jobTitle", job.getJobTitle());
                    map.put("companyName", job.getCompanyName());
                    map.put("companyLogo", job.getCompanyLogo());
                    map.put("jobGeo", job.getJobGeo());
                    map.put("jobLevel", job.getJobLevel());
                    map.put("jobDescription", job.getJobDescription());
                    map.put("pubDate", job.getPubDate());
                    Vagas vaga = new Vagas(map);
                    arrayWithNeweo.add(vaga);
               }
            }
        return arrayWithNeweo;
    }

}















