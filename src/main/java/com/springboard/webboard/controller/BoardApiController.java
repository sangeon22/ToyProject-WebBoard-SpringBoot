package com.springboard.webboard.controller;

import java.util.List;

import com.springboard.webboard.entity.Board;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.util.StringUtils;


@Slf4j
@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;
    @GetMapping("/boards")
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }


    //    @Secured("ROLE_ADMIN")
//    @PreAuthorize("isAuthenticated() and (( #board.user.username == principal.getUsername()) or hasRole('ROLE_ADMIN'))")
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id,
                     Authentication authentication) {
//        System.out.println(board.getUser().getUsername());
//        Board board = boardRepository.findById(id).orElse(null);
//        log.info("--------------------------------");
//        log.info(board.getUser().getUsername());
//        log.info("--------------------------------");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


//        this.boardService.findById(id)
//                .ifPresent(board -> {
//                    if (board.getUser().getUsername().equals(authentication.getPrincipal().getClass().getName())) {
//                        this.boardService.deleteById(id);
//                    } else {
//                        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
//                    }
//                });
    }

}