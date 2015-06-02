package com.taobao.service;

import com.taobao.entity.User;

import java.util.List;

/**
 * Created by star on 15/5/22.
 */
public interface UserService {
    public User add(User user);

    public User findById(Long id);

    public User findByName(String name);

    public List<User> findAll();
}
