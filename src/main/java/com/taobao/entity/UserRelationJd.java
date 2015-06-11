package com.taobao.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by star on 15/6/11.
 */
@Entity
@Table(name = "t_user_jd_relation")
public class UserRelationJd extends BaseDomain {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "jd_id", nullable = false)
    private JDProduct product;

    public JDProduct getProduct() {
        return product;
    }

    public void setProduct(JDProduct product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
