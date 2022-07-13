package com.springboard.webboard.config;

import com.springboard.webboard.entity.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;
    private String birth;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.birth = user.getBirth();
    }
}