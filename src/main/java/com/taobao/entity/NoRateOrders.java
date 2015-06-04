package com.taobao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 15/5/24.
 */
@Entity
@Table(name = "t_no_rate_orders")
public class NoRateOrders extends BaseDomain{
    public Float getPayment() {
        return payment;
    }

    public void setPayment(Float payment) {
        this.payment = payment;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    @Column
    private Float payment;
    @Column
    private String buyer;
    @Column
    private Long tid;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date overTime;
    @Column
    private boolean isRate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public boolean isRate() {
        return isRate;
    }

    public void setRate(boolean isRate) {
        this.isRate = isRate;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }
}
