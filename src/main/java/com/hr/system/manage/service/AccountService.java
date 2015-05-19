package com.hr.system.manage.service;

import com.hr.system.manage.repository.domain.Account;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 17:08
 * function:
 * To change this template use File | Settings | File Templates.
 */
public interface AccountService {
    public Account save(Account account);

    public Account getById(Long id);

    public Account findByName(String name);
}
