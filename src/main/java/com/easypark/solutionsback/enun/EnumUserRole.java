package com.easypark.solutionsback.enun;

public enum EnumUserRole {
    ADMIN("admin"),
    USER("user");

    private String role;

    EnumUserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
