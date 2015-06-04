package com.taobao.entity;

import javax.persistence.*;
import java.util.Date;

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Column
    private String refreshToken;



    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date overDate;

    public Date getSessionKeyOverDate() {
        return sessionKeyOverDate;
    }

    public void setSessionKeyOverDate(Date sessionKeyOverDate) {
        this.sessionKeyOverDate = sessionKeyOverDate;
    }

    @Transient
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date sessionKeyOverDate;

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

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
