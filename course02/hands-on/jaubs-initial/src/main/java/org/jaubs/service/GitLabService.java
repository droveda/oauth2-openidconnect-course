package org.jaubs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    public record GitLabProject(int id, String name, String description) {
    }

    public GitLabService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<BookItem> getBooksFromGitLab(String token) throws JsonProcessingException {
        List<GitLabProject> projects = getProjects(token);

        if (projects.isEmpty()) {
            return List.of();
        }

        var response = apiClient
                .get()
                .uri(GITLAB_API + "/projects/" + projects.get(0).id + "/repository/files/jaubs.json/raw?ref=main")
                .header("Authorization", "bearer " + token)
                .retrieve()
                .body(String.class);

        return objectMapper.readValue(response, new TypeReference<List<BookItem>>() {
        });

    }

    private List<GitLabProject> getProjects(String token) {
        return apiClient
                .get()
                .uri(GITLAB_API + "/projects?owned=true&search=jaubs")
                .header("Authorization", "bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<List<GitLabProject>>() {
                });
    }

}
