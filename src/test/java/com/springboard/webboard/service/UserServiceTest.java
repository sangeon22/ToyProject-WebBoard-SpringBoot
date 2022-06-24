package com.springboard.webboard.service;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private UserDto userDto;

    private UserDto createUser() {
        userDto.setUsername("dsd");

        return userDto;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        UserDto userDto = createUser();
        User savedUser = userService.save(userDto);

        assertEquals(userDto.getUsername(), savedUser.getUsername());
    }
}