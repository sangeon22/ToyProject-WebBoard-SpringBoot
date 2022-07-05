package com.springboard.webboard.repository;

import com.springboard.webboard.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {


    List<Board> findByTitle(String title);
    List<Board> findByTitleOrContent(String title, String content);
    List<Board> findByTitleAndContent(String title, String content);

    @Query(value = "select b from Board b where b.user.username = :name")
    Page<Board> userBoardList(@Param("name") String username, Pageable pageable);

    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

    @Modifying
    @Query(value = "update Board b set b.view = b.view + 1 where b.id = :id")
    int updateView(Long id);

    Long findByUserId(Long id);
}