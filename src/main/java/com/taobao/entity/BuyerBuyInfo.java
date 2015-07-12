package com.taobao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by star on 15/6/28.
 */
@Entity
@Table(name = "t_buyer_buy_info")
public class BuyerBuyInfo extends BaseDomain{
    @Column
    private String buyerNick;
    @Column
    private String receiverMobile;
    @Column
    private String receiverName;

    @ManyToOne(cascade= CascadeType.REFRESH, optional=false)
    @JoinColumn(name = "product_id")
    private TaoBaoProduct product;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    @Column
    private Long num;

    public String getBuyerNick() {
        return buyerNick;
    }

    public void setBuyerNick(String buyerNick) {
        this.buyerNick = buyerNick;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public TaoBaoProduct getProduct() {
        return product;
    }

    public void setProduct(TaoBaoProduct product) {
        this.product = product;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public Date getConsumedRemindDate() {
        return consumedRemindDate;
    }

    public void setConsumedRemindDate(Date consumedRemindDate) {
        this.consumedRemindDate = consumedRemindDate;
    }

    public boolean isSendConsumedSms() {
        return isSendConsumedSms;
    }

    public void setSendConsumedSms(boolean isSendConsumedSms) {
        this.isSendConsumedSms = isSendConsumedSms;
    }

    @Column
    private String itemTitle;
    @Column
    @Temporal(TemporalType.DATE)
    private Date consumedRemindDate;
    @Column
    private boolean isSendConsumedSms;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(length = 500)
    private String address;
}
