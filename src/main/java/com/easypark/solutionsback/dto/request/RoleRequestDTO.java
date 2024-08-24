package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.model.UserRole;

public record RoleRequestDTO(String username, UserRole role) {
}
