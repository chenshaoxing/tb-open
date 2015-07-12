package com.taobao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by star on 15/7/12.
 */
@Entity
@Table(name = "t_shipper_notify_delay")
public class ShipperNotifyDelay extends BaseDomain{
    @Column
    private String mobile;
    @Column
    private String smsContent;
    @Column
    private String sellerEmail;
    @Column             //是否通知成功
    private boolean isSuccess=false;
    public boolean isSuccess() {
        return isSuccess;
    } public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getSellerEmailContent() {
        return sellerEmailContent;
    }

    public void setSellerEmailContent(String sellerEmailContent) {
        this.sellerEmailContent = sellerEmailContent;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(length = 1000)
    private String sellerEmailContent;

}
