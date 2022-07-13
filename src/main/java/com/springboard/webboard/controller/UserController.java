package com.springboard.webboard.controller;

import com.springboard.webboard.dto.BoardDto;
import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.repository.UserRepository;
import com.springboard.webboard.service.BoardService;
import com.springboard.webboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping("/userlist")
    public String list(Model model,
                       @RequestParam(required = false, defaultValue = "") String searchKeyword) {
        List<UserDto> users = userService.getlist(searchKeyword);
        model.addAttribute("users", users);
        return "user/userlist";
    }

    @GetMapping("myboardlist")
    public String myBoardList(Model model,
                              @PageableDefault(size = 5)
                              @SortDefault.SortDefaults({
                                      @SortDefault(sort = "modifiedDate", direction = Sort.Direction.DESC),
                                      @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC),
                              }) Pageable pageable,
                              Authentication authentication) {
        Page<BoardDto> boards = boardService.getMyBoardList(pageable, authentication);
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

        return "user/myboardlist";
    }


    @GetMapping("/mypage")
    public String loginForm(Model model, Authentication authentication) {
        model.addAttribute("user", authentication.getName());
        return "user/mylogin";
    }

    @PostMapping("/mypage")
    public String login(Model model,
                        Authentication authentication,
                        @RequestParam("inputPassword") String inputPassword) {

        UserDto userDto = userService.findUser(authentication.getName());
        String role = authentication.getAuthorities().toString();

        switch (role) {
            case "[ROLE_USER]":
                role = "일반 회원";
                break;
            case "[ROLE_ADMIN, ROLE_USER]":
                role = "관리자 + 일반 회원";
                break;
            case "[ROLE_ADMIN]":
                role = "관리자";
                break;
        }

        String password = userRepository.findByUsername(userDto.getUsername()).getPassword();
        log.info("inputPassword = {}", inputPassword);
        log.info("password = {}", password);
        if (passwordEncoder.matches(inputPassword, password)) {
            model.addAttribute("userDto", userDto);
            model.addAttribute("role", role);
            return "user/mypage";
        } else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("searchUrl", "/users/mypage");
            return "board/message";
        }
    }


//    @PostMapping("/mypage/myinfo")
//    public String myInfo(@Valid UserDto userDto,
//                         BindingResult bindingResult,
//                         Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("userDto", userDto);
//            Map<String, String> errorMap = new HashMap<>();
//
//            for (FieldError error : bindingResult.getFieldErrors()) {
//                errorMap.put("valid_" + error.getField(), error.getDefaultMessage());
//                log.info("error message : " + error.getDefaultMessage());
//            }
//            return "/users/mypage";
//
//        } else {
//            model.addAttribute("userDto", userDto);
//            model.addAttribute("message", "회원정보가 수정되었습니다.");
//            model.addAttribute("searchUrl", "/");
//            return "redirect:/";
//        }
//
//    }
//}


//    @PostMapping("/mypage/my")
//    public String userEdit(BindingResult result, Authentication authentication) {
//        if(result.hasErrors()) {
//            return "redirect:/mypage/my";
//        }
//        UserDto userDto = userService.findUser(authentication.getName());
//        userService.updateInfo(authentication.getName(), userDto.getUsername(), userDto.getBirth());
//        authentication.setUsername(form.getName());
//        currentMember.setEmail(form.getEmail());
//
//        return "redirect:/mypage/me";
//    }


}
