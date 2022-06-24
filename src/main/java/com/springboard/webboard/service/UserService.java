package com.springboard.webboard.service;

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

    public User save(User user) {
        User input_username = userRepository.findByUsername(user.getUsername());
        if (input_username != null) {
            throw new IllegalStateException("이미 사용 중인 회원 이름입니다.");
        }

        user.setEnabled(true);
        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
}