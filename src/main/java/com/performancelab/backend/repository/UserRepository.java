package com.performancelab.backend.repository;

import com.performancelab.backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Page<User> findByFirstNameContainingIgnoreCase(
            String firstName,
            Pageable pageable
    );

}
