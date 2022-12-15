package com.springboard.webboard.config.auth.dto;

import com.springboard.webboard.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String username;
    private String email;

    public SessionUser(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}