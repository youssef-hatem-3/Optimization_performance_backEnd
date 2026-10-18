package com.performancelab.backend.dto;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        int age,
        String role,
        String country
) {}
