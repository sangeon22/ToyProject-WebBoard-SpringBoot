package com.springboard.webboard.repository;

import com.springboard.webboard.entity.User;

public interface CustomizedUserRepository {
    void findByUsernameCustom(User user);
}
