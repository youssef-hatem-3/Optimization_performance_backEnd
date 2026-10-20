
package com.performancelab.backend.service;
import com.performancelab.backend.dto.AddressDto;
import com.performancelab.backend.dto.UserDto;
import com.performancelab.backend.dto.UpdateUserRequest;
import com.performancelab.backend.entity.User;
import com.performancelab.backend.exception.UserNotFoundException;
import com.performancelab.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.performancelab.backend.dto.CreateUserRequest;
import com.performancelab.backend.dto.UpdateUserRequest;
import org.springframework.transaction.annotation.Transactional;

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


    public UserDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );

        return toDto(user);
    }

    @Transactional
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setAge(request.getAge());
        user.setRole(request.getRole());
        user.setCountry(request.getCountry());

        return toDto(user);
    }

    @Transactional
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );

        userRepository.delete(user);
    }

}
