package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumUserRole;

public record RegisterAdmRequestDTO(String username, String password, EnumUserRole role) {
}
