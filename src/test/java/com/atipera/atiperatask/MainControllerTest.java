package com.atipera.atiperatask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnRepositoriesWithBranches() throws Exception {
        mockMvc.perform(get("/Mart6DeLavi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].repositoryName").exists())
                .andExpect(jsonPath("$[0].ownerLogin").value("Mart6DeLavi"))
                .andExpect(jsonPath("$[0].branches").isArray())
                .andExpect(jsonPath("$[0].branches[0].name").exists())
                .andExpect(jsonPath("$[0].branches[0].lastCommitsSha").exists());
    }

    @Test
    void shouldNotIncludeForkedRepositories() throws Exception {
        var result = mockMvc.perform(get("/Mart6DeLavi"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, Object>> repos = mapper.readValue(result, List.class);

        for (Map<String, Object> repo : repos) {
            Assertions.assertFalse(repo.containsKey("fork"));
        }
    }

    @Test
    void shouldReturn404NotFoundExistentUser() throws Exception {
        mockMvc.perform(get("/nonExistingGithubUser123456"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void shouldReturnEmptyListForUserWithoutRepositories() throws Exception {
        mockMvc.perform(get("/ghost"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void shouldReturnProperJsonStructure() throws Exception {
        mockMvc.perform(get("/Mart6DeLavi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].repositoryName").isString())
                .andExpect(jsonPath("$[0].ownerLogin").isString())
                .andExpect(jsonPath("$[0].branches[0].name").isString())
                .andExpect(jsonPath("$[0].branches[0].lastCommitsSha").isString());
    }
}
