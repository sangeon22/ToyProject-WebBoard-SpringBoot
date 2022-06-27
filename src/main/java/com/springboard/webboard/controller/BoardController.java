package com.springboard.webboard.controller;

import com.springboard.webboard.dto.BoardDto;
import com.springboard.webboard.entity.Board;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.service.BoardService;
import com.springboard.webboard.validator.BoardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardValidator boardValidator;

    //} = "modifiedDate", direction = Sort.Direction.DESC)
    @GetMapping("/list")
    public String list(Model model,
                       @PageableDefault(size = 5)
                       @SortDefault.SortDefaults({
                               @SortDefault(sort = "modifiedDate", direction = Sort.Direction.DESC),
                               @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC),
                       }) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchKeyword) {

//        Page<Board> boards = boardRepository.findAll(pageable);
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchKeyword, searchKeyword, pageable);
//        int block = boards.getTotalPages() / 5;
//        if (boards.getTotalPages() % 5 != 0) {
//            block++;
//        }
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
    public String form(Model model, @RequestParam(required = false) Long id) {
        boardService.updateView(id);
        Board board = boardRepository.findById(id).orElse(null);
        model.addAttribute("board", board);
//        model.addAttribute("view", boardService.updateView(id));
        return "/board/boardview";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new BoardDto());
        return "board/form";
    }

    @PostMapping("/form")
    public String postForm(@Valid BoardDto boardDto,
                           BindingResult bindingResult,
                           Authentication authentication,
                           MultipartFile file,
                           Model model) throws IOException {
        boardValidator.validate(boardDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        else {
            Integer view = 0;
            String username = authentication.getName();
            boardService.save(boardDto, username, file, view);
            model.addAttribute("message", "글 작성이 완료되었습니다.");
            model.addAttribute("searchUrl", "/board/list");
            return "board/message";
        }

    }

    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model) {
//        Board board = boardRepository.findById(id).orElse(null);
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("boardDto", boardDto);
        return "board/modify";
    }


    @PreAuthorize("isAuthenticated() and (( #boardDto.toEntity() == principal.authorities) or hasRole('ROLE_ADMIN'))")
    @PostMapping("/modify/{id}")
    public String modify(@Valid BoardDto boardDto,
                         BindingResult bindingResult,
                         Authentication authentication,
                         MultipartFile file,
                         Model model) throws IOException {
        boardValidator.validate(boardDto, bindingResult);

        if (bindingResult.hasErrors()) {
            return "board/modify";
        }
        String username = boardRepository.findById(boardDto.getId()).get().getUser().getUsername();
        Integer view = boardRepository.findById(boardDto.getId()).get().getView();

        boardService.save(boardDto, username, file, view);
        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
//        boardRepository.save(board);

        return "board/message";
    }


}