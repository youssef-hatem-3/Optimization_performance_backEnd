
package com.performancelab.backend.controller;

import com.performancelab.backend.dto.AddressDto;
import com.performancelab.backend.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @GetMapping("/users")
    public Map<String, Object> getUsers() {

        List<UserDto> users = List.of(
                new UserDto(
                        1L,
                        "Ahmed",
                        "Ali",
                        "ahmed@example.com",
                        25,
                        "admin",
                        new AddressDto("Egypt")
                ),
                new UserDto(
                        2L,
                        "Omar",
                        "Hassan",
                        "omar@example.com",
                        28,
                        "user",
                        new AddressDto("Egypt")
                )
        );

        return Map.of(
                "users", users,
                "total", users.size(),
                "skip", 0,
                "limit", users.size()
        );
    }
}
