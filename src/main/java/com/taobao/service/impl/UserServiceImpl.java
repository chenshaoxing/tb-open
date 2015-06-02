package com.taobao.service.impl;

import com.taobao.dao.IBasePersistence;
import com.taobao.entity.User;
import com.taobao.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by star on 15/5/22.
 */
@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {
    @Resource
    private IBasePersistence iBasePersistence;
    @Override
    public User add(User user) {
        return iBasePersistence.save(user);
    }

    @Override
    public User findById(Long id) {
        User user = new User();
        user.setId(id);
        return iBasePersistence.getEntityById(User.class,user);
    }

    @Override
    public User findByName(String nickname) {
        return iBasePersistence.getEntityByField(User.class,"nickname",nickname);
    }

    @Override
    public List<User> findAll() {
        return iBasePersistence.getAllEntities(User.class);
    }
}
