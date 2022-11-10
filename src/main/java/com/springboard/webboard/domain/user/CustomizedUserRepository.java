package com.springboard.webboard.domain.user;

import com.springboard.webboard.domain.user.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernameJdbc(String username);
}
