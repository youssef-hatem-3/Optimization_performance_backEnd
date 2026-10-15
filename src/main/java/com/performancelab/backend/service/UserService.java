
package com.performancelab.backend.service;

import com.performancelab.backend.dto.AddressDto;
import com.performancelab.backend.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    public List<UserDto> getUsers() {
        return List.of(
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
    }

    public int getUsersCount() {
        return getUsers().size();
    }
}
