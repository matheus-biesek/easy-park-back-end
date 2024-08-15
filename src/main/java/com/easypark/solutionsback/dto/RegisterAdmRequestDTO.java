package com.easypark.solutionsback.dto;

import com.easypark.solutionsback.model.UserRole;

public record RegisterAdmRequestDTO(String username, String password, UserRole role) {
}
