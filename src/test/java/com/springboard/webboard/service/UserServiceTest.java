package com.springboard.webboard.service;

import com.springboard.webboard.web.dto.UserDto;
import com.springboard.webboard.domain.user.User;
import com.springboard.webboard.domain.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

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


    @Test
    @Transactional
    @Rollback
    public void 중복회원예외(){
        //given
        UserDto userDto1 = new UserDto();
        userDto1.setUsername("hello");
        userDto1.setPassword("test12!");
        userDto1.setEmail("testcode1@naver.com");
        userDto1.setBirth("19951218");

        UserDto userDto2 = new UserDto();
        userDto2.setUsername("hello");
        userDto2.setPassword("test13!");
        userDto2.setEmail("testcode1@naver.com");
        userDto2.setBirth("19951218");

        // when
        userService.save(userDto1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.save(userDto2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 사용 중인 회원 이름입니다.");

    }


    @Test
    @Transactional
    @Rollback
    public void 회원탈퇴(){

    }

    @DisplayName("비밀번호 수정 - 입력값 정상")
    @Test
    void 비밀번호변경성공() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String CurrentPassword = encoder.encode("test123!");
        String CurrentCheckPassword = "test123!";
        String NewPassword = "test1234!";
        String RePassword = "test1234!";

        String code = userService.passwordCheck(CurrentPassword, CurrentCheckPassword, NewPassword, RePassword);
        assertThat(code).isEqualTo("ok");
    }

    @DisplayName("비밀번호 수정 - 입력값 에러 - 현재 비밀번호 불일치")
    @Test
    void 비밀번호변경실패1() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String CurrentPassword = encoder.encode("test123!");
        String CurrentCheckPassword = "t343525!";
        String NewPassword = "test1234!";
        String RePassword = "test1234!";

        String code = userService.passwordCheck(CurrentPassword, CurrentCheckPassword, NewPassword, RePassword);
        assertThat(code).isEqualTo("현재 패스워드 불일치");
    }

    @DisplayName("비밀번호 수정 - 입력값 에러 - 현재 비밀번호와 새 비밀번호 일치")
    @Test
    void 비밀번호변경실패2() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String CurrentPassword = encoder.encode("test123!");
        String CurrentCheckPassword = "test123!";
        String NewPassword = "test123!";
        String RePassword = "test123!";

        String code = userService.passwordCheck(CurrentPassword, CurrentCheckPassword, NewPassword, RePassword);
        assertThat(code).isEqualTo("동일한 패스워드");
    }

    @DisplayName("비밀번호 수정 - 입력값 에러 - 비밀번호 재확인 불일치")
    @Test
    void 비밀번호변경실패3() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String CurrentPassword = encoder.encode("test123!");
        String CurrentCheckPassword = "test123!";
        String NewPassword = "test1234!";
        String RePassword = "test1!";

        String code = userService.passwordCheck(CurrentPassword, CurrentCheckPassword, NewPassword, RePassword);
        assertThat(code).isEqualTo("새 패스워드 불일치");
    }


}