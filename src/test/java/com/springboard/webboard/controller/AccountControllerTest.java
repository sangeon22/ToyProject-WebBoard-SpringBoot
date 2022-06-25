package com.springboard.webboard.controller;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.UserRepository;
import com.springboard.webboard.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountControllerTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    @Transactional
    @Rollback(false)
    public void joinMember(){
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser16");
        userDto.setPassword("xptmxm123!");
        userDto.setBirth("19951218");

        User joinUser = userService.save(userDto);

        User findUser = userRepository.findByUsername(joinUser.getUsername());
        assertThat(findUser.getUsername()).isEqualTo(userDto.getUsername());
        assertThat(findUser).isEqualTo(joinUser);
    }


}