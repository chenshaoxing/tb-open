package com.taobao.entity;

import javax.persistence.*;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-19
 * Time: 18:08
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_black_list")
public class BlackList extends BaseDomain {
    public String getBuyerNickname() {
        return buyerNickname;
    }

    public void setBuyerNickname(String buyerNickname) {
        this.buyerNickname = buyerNickname;
    }

    @Column
    private String buyerNickname;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
