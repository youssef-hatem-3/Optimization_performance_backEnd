
package com.performancelab.backend.service;
import com.performancelab.backend.dto.AddressDto;
import com.performancelab.backend.dto.UserDto;
import com.performancelab.backend.dto.UpdateUserRequest;
import com.performancelab.backend.entity.User;
import com.performancelab.backend.exception.EmailAlreadyExistsException;
import com.performancelab.backend.exception.UserNotFoundException;
import com.performancelab.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.performancelab.backend.dto.CreateUserRequest;
import com.performancelab.backend.dto.UpdateUserRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.performancelab.backend.dto.PagedUsersResponse;
import org.springframework.data.domain.Sort;
import org.springframework.web.server.ResponseStatusException;

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

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(
                    request.email()
            );
        }

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



    public PagedUsersResponse getUsersPaginated(
            int page,
            int size,
            String sortBy,
            String direction,
            String search
    ){
        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid page or size"
            );
        }

        Boolean sortByList = List.of("id", "firstName", "age", "email").contains(sortBy) ;

        if (!sortByList) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported sort field"
            );
        }

        Sort.Direction sortDirection;

        try {
            sortDirection = Sort.Direction
                    .fromString(direction);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Direction must be asc or desc"
            );
        }

        Sort sort = Sort.by(sortDirection, sortBy);

        if (!sortBy.equals("id")) {
            sort = sort.and(Sort.by("id"));
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                sort
        );


        Page<User> usersPage;

        if (search == null || search.isBlank()) {
            usersPage = userRepository.findAll(pageable);
        } else {
            usersPage =
                    userRepository.findByFirstNameContainingIgnoreCase(
                            search.trim(),
                            pageable
                    );
        }


        return new PagedUsersResponse(
                usersPage.map(this::toDto).getContent(),
                usersPage.getTotalElements(),
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalPages(),
                usersPage.hasNext()
        );
    }



}
