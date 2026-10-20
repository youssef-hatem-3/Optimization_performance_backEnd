package com.performancelab.backend.dto;
import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Email
        String email,

        @Min(18)
        int age,

        @NotBlank
        String role,

        @NotBlank
        String country

) {}
