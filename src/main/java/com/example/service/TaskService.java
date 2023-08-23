package com.example.service;

import com.example.dto.TaskDTO;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class TaskService {
    private String url = "http://localhost:8081/";

    public void taskList() {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<TaskDTO>> returnType = new ParameterizedTypeReference<List<TaskDTO>>() {
        };
        try {
            RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET, new URI(url + "task/getAll"));
            ResponseEntity<List<TaskDTO>> response = restTemplate.exchange(request, returnType);
            List<TaskDTO> list = response.getBody();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    public void createTask(TaskDTO dto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url + "task", dto, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }

    @Test
    public void getById(String id) {
        RestTemplate restTemplate = new RestTemplate();
        TaskDTO taskDTO = restTemplate.getForObject(url + "task/" + id, TaskDTO.class);
    }


    public void delete(String id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url + "task/" + id);
    }

    public void delete2(String id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url + "task/{id}", id);
    }


    public void createTask2(TaskDTO dto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<TaskDTO> request = new HttpEntity<TaskDTO>(dto);
        ResponseEntity<String> response = restTemplate.postForEntity(url + "task", request, String.class);
        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());
    }


    public void getById2(String id) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TaskDTO> request = new HttpEntity<TaskDTO>(headers);

        ResponseEntity<TaskDTO> responseEntity =
                restTemplate.exchange(url + "task/" + id, HttpMethod.GET, request, TaskDTO.class);
    }

}
