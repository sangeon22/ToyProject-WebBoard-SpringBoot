package com.springboard.webboard.controller;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.UserRepository;
import com.springboard.webboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/userlist")
    public String all(Model model,
                      @RequestParam(required = false, defaultValue = "") String searchKeyword) {
        List<UserDto> users = userService.getlist(searchKeyword);
        model.addAttribute("users",users);
        return "user/userlist";
    }


}
