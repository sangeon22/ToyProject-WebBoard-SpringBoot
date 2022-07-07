package com.springboard.webboard.dto;


import com.springboard.webboard.entity.User;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "입력하신 아이디는 아래의 형식에 맞지 않습니다.")
    private String username;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    @Email(message = "입력하신 이메일은 아래의 형식에 맞지 않습니다.")
//    @Pattern(regexp="^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "입력하신 이메일은 아래의 형식에 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "입력하신 비밀번호는 아래의 형식에 맞지 않습니다.")
    private String password;

    @NotBlank(message = "생년월일은 필수 입력값입니다.")
    @Pattern(regexp = "^(19[0-9][0-9]|20\\d{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])$", message = "입력하신 생년월일은 8자리 형식에 맞지 않습니다.")
    private String birth;




    @Builder
    public UserDto(Long id, String username, String email, String password, String birth) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birth = birth;
    }

    public User toEntity(){
        return User.builder()
                .username(username)
                .email(email)
                .password(new BCryptPasswordEncoder().encode(password))
                .birth(birth)
                .build();
    }

}
