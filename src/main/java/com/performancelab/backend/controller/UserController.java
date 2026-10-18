
package com.performancelab.backend.controller;

import com.performancelab.backend.service.UserService;
import com.performancelab.backend.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import com.performancelab.backend.dto.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(
            @RequestBody CreateUserRequest request
    ) {
        UserDto createdUser = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

}

