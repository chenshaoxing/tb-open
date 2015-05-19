package com.hr.system.manage.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-03-31
 * Time: 17:06
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Account extends BaseDomain{
    @Column
    private String username;
    @Column
    private String pwd;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
