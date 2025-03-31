package com.atipera.atiperatask.service;

import com.atipera.atiperatask.dto.request.BranchDto;
import com.atipera.atiperatask.dto.request.RepositoryDto;
import com.atipera.atiperatask.dto.response.BranchResponseDto;
import com.atipera.atiperatask.dto.response.RepositoryResponseDto;
import com.atipera.atiperatask.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainFunctionalService {
    private final RestTemplate restTemplate;

    public List<RepositoryResponseDto> getAllUserRepositories(String username) {
        try {
            String url = String.format("https://api.github.com/users/%s/repos", username);

            ResponseEntity<RepositoryDto[]> response = restTemplate.getForEntity(url, RepositoryDto[].class);
            RepositoryDto[] repositories = response.getBody();

            if (repositories == null) {
                return List.of();
            }

            return Arrays.stream(repositories)
                    .filter(repository -> !repository.fork())
                    .map(repository -> {
                        String branchesUrl = repository.branchUrl().replace("{/branch}", "");
                        ResponseEntity<BranchDto[]> branchResponse =
                                restTemplate.getForEntity(branchesUrl, BranchDto[].class);

                        List<BranchResponseDto> branches = Arrays.stream(branchResponse.getBody())
                                .map(branch -> new BranchResponseDto(branch.name(), branch.commit().sha()))
                                .toList();

                        return new RepositoryResponseDto(repository.name(), repository.owner().login(), branches);
                    })
                    .toList();

        } catch (HttpClientErrorException.NotFound ex) {
            throw new UserNotFoundException("User not found");
        }
    }

}
