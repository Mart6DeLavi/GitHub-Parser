package com.atipera.atiperatask.controller;

import com.atipera.atiperatask.dto.response.RepositoryResponseDto;
import com.atipera.atiperatask.service.MainFunctionalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainFunctionalService service;

    @GetMapping("/{username}")
    public List<RepositoryResponseDto> getAllUserRepository(@PathVariable("username") String username) {
        return service.getAllUserRepositories(username);
    }
}
