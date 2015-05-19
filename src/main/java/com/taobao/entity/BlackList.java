package com.taobao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
}
