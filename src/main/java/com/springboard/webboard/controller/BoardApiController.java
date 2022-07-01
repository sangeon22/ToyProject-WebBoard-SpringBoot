package com.springboard.webboard.controller;

import java.util.List;

import com.springboard.webboard.dto.BoardDto;
import com.springboard.webboard.entity.Board;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


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
//    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/boards/{id}")
    ResponseEntity deleteBoard(@PathVariable Long id,
                     BoardDto boardDto,
                     Authentication authentication,
                     HttpServletRequest request,
                     Model model) {
        String username = boardRepository.findById(boardDto.getId()).get().getUser().getUsername();
        if (authentication.getName().equals(username) || request.isUserInRole("ROLE_ADMIN")) {
            boardService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }
}