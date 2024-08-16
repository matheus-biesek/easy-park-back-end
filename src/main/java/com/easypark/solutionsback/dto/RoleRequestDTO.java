package com.easypark.solutionsback.dto;

import com.easypark.solutionsback.model.UserRole;

public record RoleRequestDTO(String username, UserRole role) {
}
