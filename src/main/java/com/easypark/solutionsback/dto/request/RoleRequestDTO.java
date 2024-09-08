package com.easypark.solutionsback.dto.request;

import com.easypark.solutionsback.enun.EnumUserRole;

public record RoleRequestDTO(String username, EnumUserRole role) {
}
