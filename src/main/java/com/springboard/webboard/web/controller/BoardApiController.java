package com.springboard.webboard.web.controller;

import java.util.List;

import com.springboard.webboard.config.auth.LoginUser;
import com.springboard.webboard.config.auth.dto.SessionUser;
import com.springboard.webboard.web.dto.BoardDto;
import com.springboard.webboard.domain.board.Board;
import com.springboard.webboard.domain.board.BoardRepository;
import com.springboard.webboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
class BoardApiController {

    private final BoardRepository repository;
    private final BoardService boardService;
    private final HttpSession httpSession;

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
                     @LoginUser SessionUser user,
                     BoardDto boardDto,
                     Authentication authentication,
                     HttpServletRequest request) {
        String username = boardRepository.findById(boardDto.getId()).get().getUser().getUsername();
        if (authentication.getName().equals(username) || request.isUserInRole("ROLE_ADMIN") || user.getUsername().equals(username)) {
            boardService.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

    }
}