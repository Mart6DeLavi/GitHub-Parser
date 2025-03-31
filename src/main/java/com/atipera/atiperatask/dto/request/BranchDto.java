package com.atipera.atiperatask.dto.request;

public record BranchDto(
        String name,
        CommitDto commit
) {
}
