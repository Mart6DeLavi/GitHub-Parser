package com.atipera.atiperatask.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record RepositoryResponseDto(
        String repositoryName,
        String ownerLogin,
        List<BranchResponseDto> branches
) {
}
