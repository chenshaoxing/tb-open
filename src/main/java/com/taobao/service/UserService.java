package com.taobao.service;

import com.taobao.entity.User;

/**
 * Created by star on 15/5/22.
 */
public interface UserService {
    public User add(User user);

    public User findById(Long id);

    public User findByName(String name);
}
