
package com.performancelab.backend.dto;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        int age,
        String role,
        AddressDto address
) {}
