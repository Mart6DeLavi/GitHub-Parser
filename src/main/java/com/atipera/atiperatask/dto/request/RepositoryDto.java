package com.atipera.atiperatask.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record RepositoryDto(
        String name,
        boolean fork,
        OwnerDto owner,

        @JsonProperty("branches_url")
        String branchUrl
) {
}
