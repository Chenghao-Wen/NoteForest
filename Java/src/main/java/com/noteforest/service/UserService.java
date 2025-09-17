package com.noteforest.service;

import com.noteforest.pojo.User;

public interface UserService {
    // Find by username
    User findByUsername(String username);

    // Register
    void register(String username, String password);

    // Update
    void update(User user);

    // Update avatar
    void updateAvatar(String url);

    void updatePwd(String md5String, Integer id);
}
