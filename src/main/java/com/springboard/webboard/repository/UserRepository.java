package com.springboard.webboard.repository;

import com.springboard.webboard.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = { "boards" })
    List<User> findAll();

    User findByUsername(String username);
}
