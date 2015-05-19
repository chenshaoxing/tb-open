package com.taobao.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-19
 * Time: 17:36
 * function:
 * 自动评价日志
 */
@Entity
@Table(name = "t_auto_rate_log")
public class AutoRateLog extends BaseDomain {
    @Column
    private Long oid;
    @Column
    @Enumerated(EnumType.STRING)
    private RateStatus rateStatus;
    @Column
    private String tradeType;
    @Column
    private String buyerNickname;
    @Column
    private Float realPrice;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date rateTime;

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="rate_content_id")
    private RateContent rateContent;

    public RateContent getRateContent() {
        return rateContent;
    }

    public void setRateContent(RateContent rateContent) {
        this.rateContent = rateContent;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public RateStatus getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(RateStatus rateStatus) {
        this.rateStatus = rateStatus;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBuyerNickname() {
        return buyerNickname;
    }

    public void setBuyerNickname(String buyerNickname) {
        this.buyerNickname = buyerNickname;
    }

    public Float getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Float realPrice) {
        this.realPrice = realPrice;
    }

    public Date getRateTime() {
        return rateTime;
    }

    public void setRateTime(Date rateTime) {
        this.rateTime = rateTime;
    }
}
