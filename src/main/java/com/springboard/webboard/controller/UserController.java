package com.springboard.webboard.controller;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.UserRepository;
import com.springboard.webboard.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/mypage/my")
    public String myPage() {
        return "user/mypage";
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
        String password = userRepository.findByUsername(userDto.getUsername()).getPassword();
        log.info("inputPassword = {}", inputPassword);
        log.info("password = {}", password);
        if (passwordEncoder.matches(inputPassword, password)) {
            model.addAttribute("userDto", userDto);
            return "user/mypage";
        } else {
            model.addAttribute("message", "비밀번호가 일치하지 않습니다.");
            model.addAttribute("searchUrl", "/users/mypage");
            return "board/message";
        }


    }


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
