package com.taobao.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-04
 * Time: 18:07
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_taobao_product")
public class TaoBaoProduct extends BaseDomain {

    @ManyToOne(cascade=CascadeType.REFRESH, optional=false)
    @JoinColumn(name = "user_id")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    private Long numIid;
    @Column
    private Float price;

    public Float getDifferenceOfPrices() {
        return differenceOfPrices;
    }

    public void setDifferenceOfPrices(Float differenceOfPrices) {
        this.differenceOfPrices = differenceOfPrices;
    }

    @Column
    private Float differenceOfPrices;  //差价

    @Column                  //消耗周期 Day为单位
    private Integer cyclical;

    public Integer getCyclical() {
        return cyclical;
    }

    public void setCyclical(Integer cyclical) {
        this.cyclical = cyclical;
    }

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
