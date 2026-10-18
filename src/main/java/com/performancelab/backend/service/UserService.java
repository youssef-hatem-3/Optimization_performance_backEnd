
package com.performancelab.backend.service;

import com.performancelab.backend.dto.AddressDto;
import com.performancelab.backend.dto.UserDto;
import com.performancelab.backend.entity.User;
import com.performancelab.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.performancelab.backend.dto.CreateUserRequest;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> toDto(user))
                .toList();
    }

    public long countUsers() {
        return userRepository.count();
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::toDto);
    }

    private UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAge(),
                user.getRole(),
                new AddressDto(user.getCountry())
        );
    }

    public int getUsersCount() {
        return getUsers().size();
    }


    public UserDto createUser(CreateUserRequest request) {

        User user = new User(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.age(),
                request.role(),
                request.country()
        );

        User savedUser = userRepository.save(user);

        return toDto(savedUser);
    }


}
