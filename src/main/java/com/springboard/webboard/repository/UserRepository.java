package com.springboard.webboard.repository;

import com.springboard.webboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Board, Long> {
}
