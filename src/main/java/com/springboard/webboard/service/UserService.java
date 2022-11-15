package com.springboard.webboard.service;

import com.springboard.webboard.web.dto.UserDto;
import com.springboard.webboard.domain.board.Board;
import com.springboard.webboard.domain.Role;
import com.springboard.webboard.domain.user.User;
import com.springboard.webboard.domain.board.BoardRepository;
import com.springboard.webboard.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

//    @Autowired
//    private JavaMailSender javaMailSender;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

//    @Transactional
//    public void processNewUser(UserDto userDto) {
//        User user = save(userDto);
//        sendSignUpConfirmEmail(user);
//    }
//
//    private void sendSignUpConfirmEmail(User user) {
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(user.getEmail());
//        simpleMailMessage.setSubject("어니언 웹사이트 회원가입 메일 인증");
//        simpleMailMessage.setText("/check-email-token?token=" + user.getEmailCheckToken() + "&email=" + user.getEmail());
//        javaMailSender.send(simpleMailMessage);
//    }

    public void updatePassword(String username, String encodedNewPassword) {
        User user = userRepository.findByUsername(username);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
    }

    public String passwordCheck(String CurrentPassword, String CurrentCheckPassword, String NewPassword, String RePassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(CurrentCheckPassword, CurrentPassword)) {
            return "현재 패스워드 불일치";
        }

        if (NewPassword.equals(CurrentCheckPassword)) {
            return "동일한 패스워드";
        }


        if (!NewPassword.equals(RePassword)) {
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