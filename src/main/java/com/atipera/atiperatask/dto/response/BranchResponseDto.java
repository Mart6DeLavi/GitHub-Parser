package com.atipera.atiperatask.dto.response;

import lombok.Builder;

@Builder
public record BranchResponseDto(
        String name,
        String lastCommitsSha
) {
}
