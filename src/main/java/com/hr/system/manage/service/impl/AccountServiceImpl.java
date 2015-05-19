package com.hr.system.manage.service.impl;

import com.hr.system.manage.repository.dao.IBasePersistence;
import com.hr.system.manage.repository.domain.Account;
import com.hr.system.manage.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 17:08
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private IBasePersistence basePersistence;

    @Override
    public Account save(Account account) {
        return basePersistence.save(account);
    }

    @Override
    public Account getById(Long id) {
        Account account = new Account();
        account.setId(id);
        return basePersistence.getEntityById(Account.class,account);
    }

    @Override
    public Account findByName(String name) {
        return basePersistence.getEntityByField(Account.class,"username",name);
    }
}
