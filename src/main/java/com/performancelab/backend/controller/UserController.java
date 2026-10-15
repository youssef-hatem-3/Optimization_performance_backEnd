
package com.performancelab.backend.controller;

import com.performancelab.backend.service.UserService;
import com.performancelab.backend.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Map<String, Object> getUsers() {

        List<UserDto> users = userService.getUsers();

        return Map.of(
                "users", users,
                "total", users.size(),
                "skip", 0,
                "limit", users.size()
        );
    }

    @GetMapping("/users/count")
    public Map<String, Integer> getUsersCount() {
        return Map.of("count", userService.getUsersCount());
    }
}
