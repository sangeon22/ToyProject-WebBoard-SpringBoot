package com.springboard.webboard.service;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.Role;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserDto findUser(String username) {
        User userWrapper = userRepository.findByUsername(username);
        if ( userWrapper != null ) {
            UserDto userDto = UserDto.builder()
                    .username(userWrapper.getUsername())
                    .birth(userWrapper.getBirth())
                    .build();

            return userDto;
        }

        return null;
    }


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

    @Transactional
    public List getlist(String searchKeyword) {
        return userRepository.findByUsernameJPQLQuery(searchKeyword);
    }

//    @Transactional
//    public Long updateInfo(String username, String newName, String birth){
//        User user  = userRepository.findByUsername(username)
//                .orElseThrow(()-> new UsernameNotFoundException(username));
//
//        user.setUsername(newName);
//        user.setBirth(birth);
//        return user.getId();
//    }
}