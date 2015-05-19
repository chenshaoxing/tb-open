package com.taobao.entity;

import javax.persistence.*;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-19
 * Time: 17:00
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_user")
public class User extends BaseDomain{

    //optional = false指明IDCard可为空
    //mappedBy="person"指明Person作为双向关系的维护段，负责外键的更新，起主导作用
    @OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy="user")
    private AutoRateSetting autoRateSetting;

    @Column
    private String nickname;
    @Column
    private String sessionKey;
    @Column
    private String email;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
