package com.taobao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 15/5/24.
 */
@Entity
@Table(name = "t_no_rate_orders")
public class NoRateOrders extends BaseDomain{
    @Column
    private Long tid;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date overTime;
    @Column
    private boolean isRate;

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
