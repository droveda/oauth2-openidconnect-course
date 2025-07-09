package com.droveda.bugtrackercli.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GitLabService {

    @Value("${gitlab.api.url}")
    private String GITLAB_API;

    private final RestClient apiClient = RestClient.create();

    public record GitLabProject(int id, String name, String description) {
    }

    public List<GitLabProject> getProjects(String token) {
        return apiClient
                .get()
                .uri(GITLAB_API + "/projects?owned=true")
                .header("Authorization", "bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<List<GitLabProject>>() {
                });
    }

}
