package com.springboard.webboard.web.dto;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PasswordForm {

    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
//    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "입력하신 비밀번호는 아래의 형식에 맞지 않습니다.")
    private String password;

    @NotBlank(message = "새 비밀번호는 필수 입력값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "입력하신 비밀번호는 아래의 형식에 맞지 않습니다.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인은 필수 입력값입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "입력하신 비밀번호는 아래의 형식에 맞지 않습니다.")
    private String rePassword;

}
