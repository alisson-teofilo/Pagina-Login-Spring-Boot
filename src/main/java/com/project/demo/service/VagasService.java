package com.project.demo.service;

import com.project.demo.dto.requestDTO.VagasRequestDTO;
import com.project.demo.dto.responseDTO.VagasResponseDTO;
import com.project.demo.exeption.RegrasNegocioException;
import com.project.demo.model.Vagas;
import com.project.demo.repository.VagasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
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

    public List<VagasResponseDTO> searchJobs(String jobParamSearch) throws IOException, InterruptedException {
        List<Vagas> jobs = repository.searchJobs(jobParamSearch);

        searchInApi();

        return VagasResponseDTO.convert(jobs);
    }

    private void searchInApi() throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://workable.p.rapidapi.com/%7BAPIKEY%7D/jobs?phase=published"))
                .header("X-RapidAPI-Key", "7fce903ae4mshf9400038e709182p131457jsnb548d1c3fd8f")
                .header("X-RapidAPI-Host", "workable.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
