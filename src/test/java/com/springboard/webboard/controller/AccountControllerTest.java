package com.springboard.webboard.controller;

import com.springboard.webboard.web.dto.UserDto;
import com.springboard.webboard.domain.user.User;
import com.springboard.webboard.domain.user.UserRepository;
import com.springboard.webboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AccountControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @Transactional
    @Rollback
    public void 회원가입(){
        UserDto userDto = new UserDto();
        userDto.setUsername("testcode123");
        userDto.setPassword("test12!");
        userDto.setEmail("testcode1@naver.com");
        userDto.setBirth("19951218");

        User joinUser = userService.save(userDto);

        User findUser = userRepository.findByUsername(joinUser.getUsername());
        assertThat(findUser.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(findUser).isEqualTo(joinUser);
    }


}