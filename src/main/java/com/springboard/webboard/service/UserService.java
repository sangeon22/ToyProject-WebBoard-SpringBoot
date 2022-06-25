package com.springboard.webboard.service;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.Role;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(UserDto userDto) {
        User input_username = userRepository.findByUsername(userDto.getUsername());
        if (input_username != null) {
            throw new IllegalStateException("이미 사용 중인 회원 이름입니다.");
        }
        User user = userDto.toEntity();

        Role role = new Role();
        user.setEnabled(true);
        role.setId(1L);
        user.getRoles().add(role);
//        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
//        user.setPassword(encodedPassword);
//        user.setUsername(userDto.getUsername());
//        user.setBirth(userDto.getBirth());
//        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);

    }
}