
package com.performancelab.backend.controller;

import com.performancelab.backend.dto.UpdateUserRequest;
import com.performancelab.backend.service.UserService;
import com.performancelab.backend.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import com.performancelab.backend.dto.CreateUserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        UserDto createdUser = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PutMapping("/users/{id}")
    public UserDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        return userService.updateUser(id, request);
    }


}

