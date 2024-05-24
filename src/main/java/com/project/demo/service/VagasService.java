package com.project.demo.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.demo.dto.requestDTO.VagasRequestDTO;
import com.project.demo.dto.responseDTO.VagasResponseDTO;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.model.ModelJson;
import com.project.demo.model.Vagas;
import com.project.demo.repository.VagasRepository;
import com.project.demo.util.JsonFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
@Service
public class VagasService {

    private VagasRepository repository;

    @Autowired
    public VagasService(VagasRepository vagasRepository){
        this.repository = vagasRepository;
    }

    public List<VagasResponseDTO> jobList()
    {
        List<Vagas> retorno = repository.jobList();
        if(retorno.isEmpty()){
            throw new RegrasNegocioException("Erro ao listar vagas");
        }
        return VagasResponseDTO.convert(retorno);
    }

    public void jobInsert(VagasRequestDTO requestDTO)
    {
        repository.jobInsert(requestDTO);
    }

    public void updateJobs(VagasRequestDTO vagasReqeuest)
    {
        int registroAtualizado = repository.jobUpdate(vagasReqeuest);
    }

    public List<VagasResponseDTO> searchJobs(String jobParamSearch) throws IOException, InterruptedException
    {
        List<Vagas> jobs = repository.searchJobs(jobParamSearch);

        HttpResponse<String> response = searchInApi(jobParamSearch);

        System.out.println("response" + response.body());

        jsonFormat(response.body());

        return VagasResponseDTO.convert(jobs);
    }

    // Consome uma api para encontrar vagas
    private HttpResponse<String> searchInApi(String jobParamSearch)
    {
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

    // Formata o retorno da api em um Json. new BeanPropertyRowMapper<>
    private void jsonFormat(String response) throws JsonProcessingException
    {

        String formattedJson = JsonFormatter.formatJson(response);

        System.out.println(formattedJson);
    }


}















