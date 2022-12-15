package com.springboard.webboard.web.controller;

import com.springboard.webboard.config.auth.LoginUser;
import com.springboard.webboard.config.auth.dto.SessionUser;
import com.springboard.webboard.web.dto.BoardDto;
import com.springboard.webboard.domain.board.BoardRepository;
import com.springboard.webboard.service.BoardService;
import com.springboard.webboard.config.validator.BoardValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final BoardValidator boardValidator;
    private final HttpSession httpSession;


    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(size = 5)
                       @SortDefault.SortDefaults({
                               @SortDefault(sort = "modifiedDate", direction = Sort.Direction.DESC),
                               @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC),
                       }) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchKeyword) {


        Page<BoardDto> boards = boardService.getList(pageable, searchKeyword);
        int block = 5;
        int startBlockPage = ((boards.getPageable().getPageNumber()) / block) * block + 1;
        int endBlockPage = startBlockPage + block - 1;
        if (endBlockPage > boards.getTotalPages()) {
            endBlockPage = boards.getTotalPages();
        }
//        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
//        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);

//        int startPage = 1;
//        int endPage = boards.getTotalPages();
        model.addAttribute("startPage", startBlockPage);
        model.addAttribute("endPage", endBlockPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }


    @GetMapping("/boardview")
    public String form(@RequestParam(required = false) Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        boardService.updateView(id);
        boardDto.setView(boardService.boardView(id) + 1);
//        String username = boardDto.getUser().getUsername();
//        log.info("============================");
//        log.info("============================");
//        log.info("{}", username);
//        log.info("============================");
//        log.info("============================");
        model.addAttribute("boardDto", boardDto);
        return "/board/boardview";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid BoardDto boardDto,
                           @LoginUser SessionUser user,
                           BindingResult bindingResult,
                           Authentication authentication,
                           MultipartFile file,
                           Model model) throws IOException {
        boardValidator.validate(boardDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        } else {
            Integer view = 0;
            String username = null;
            if (authentication.getName().matches("[+-]?\\d*(\\.\\d+)?")) {
                username = user.getUsername();
            } else {
                username = authentication.getName();
            }

            boardService.save(boardDto, username, file, view);
            model.addAttribute("message", "글 작성이 완료되었습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message/message";
        }

    }

    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/modify";
    }


    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modify/{id}")
    public String modify(@Valid BoardDto boardDto,
                         @LoginUser SessionUser user,
                         BindingResult bindingResult,
                         Authentication authentication,
                         MultipartFile file,
                         HttpServletRequest request,
                         Model model) throws IOException {
        boardValidator.validate(boardDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/modify";
        }
        String username = boardRepository.findById(boardDto.getId()).get().getUser().getUsername();
        Integer view = boardRepository.findById(boardDto.getId()).get().getView();
        if (authentication.getName().equals(username) || request.isUserInRole("ROLE_ADMIN") || user.getUsername().equals(username)) {
            boardService.save(boardDto, username, file, view);
            model.addAttribute("message", "글 수정이 완료되었습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message/message";
        } else {
            model.addAttribute("message", "해당 게시글 수정 권한이 없습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "message/message";
        }
    }


}