package com.springboard.webboard.service;

import com.springboard.webboard.dto.UserDto;
import com.springboard.webboard.entity.Board;
import com.springboard.webboard.entity.Role;
import com.springboard.webboard.entity.User;
import com.springboard.webboard.repository.BoardRepository;
import com.springboard.webboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto findUser(String username) {
        User userWrapper = userRepository.findByUsername(username);
        if (userWrapper != null) {
            UserDto userDto = UserDto.builder()
                    .id(userWrapper.getId())
                    .username(userWrapper.getUsername())
                    .password(userWrapper.getPassword())
                    .email(userWrapper.getEmail())
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
        user.generateEmailCheckToken();
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

    @Transactional
    public void deleteUser(Long id) {
        String username = userRepository.findById(id).get().getUsername();
        User user = userRepository.findByUsername(username);
        if (!user.getBoards().isEmpty()) {
            for (Board board : user.getBoards()) {
                boardRepository.deleteById(board.getId());
            }
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void processNewUser(UserDto userDto) {
        User user = save(userDto);
        sendSignUpConfirmEmail(user);
    }

    private void sendSignUpConfirmEmail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("어니언 웹사이트 회원가입 메일 인증");
        simpleMailMessage.setText("/check-email-token?token=" + user.getEmailCheckToken() + "&email=" + user.getEmail());
        javaMailSender.send(simpleMailMessage);
    }

    public void updatePassword(String username, String encodedNewPassword) {
        User user = userRepository.findByUsername(username);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }

    public String passwordCheck(Model model, String CurrentPassword, String CurrentCheckPassword, String NewPassword, String RePassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(CurrentCheckPassword, CurrentPassword)) {
            model.addAttribute("error", "현재 패스워드 불일치");
            return "현재 패스워드 불일치";
        }

        if (NewPassword.equals(CurrentCheckPassword)) {
            model.addAttribute("error", "동일한 패스워드");
            return "동일한 패스워드";
        }


        if (!NewPassword.equals(RePassword)) {
            model.addAttribute("error", "새 패스워드 불일치");
            return "새 패스워드 불일치";
        }

        return "ok";
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