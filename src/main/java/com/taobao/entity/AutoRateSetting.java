package com.taobao.entity;

import javax.persistence.*;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-19
 * Time: 17:09
 * function:
 * 自动评价设置
 */
@Entity
@Table(name = "t_auto_rate_setting")
public class AutoRateSetting extends BaseDomain {

    //unique= true 指明personid列的值不可重复。
    //optional = false指明Person不可为空
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id",referencedColumnName="id", unique = true)
    private User user;


    public enum RateType{
        GOOD("good"),NEUTRAL("neutral"),BAD("bad");

        private String value;

        RateType(String value) {
            this.value = value;
        }

        public String value(){
            return this.value();
        }
    }

    public enum TriggerMode{
        //用户确认收货后立即给评价
        BUYER_CONFIRM_RIGHT_AWAY_RATE,
        //对方评价后立即给评价
        BUYER_RATE_RIGHT_AWAY_RATE,
        //对方评价后暂不给评价
        BUYER_RATE_NOT_RATE
    }

    @Column
    private boolean autoRateStatus; //开启 OR 关闭 自动评价

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isAutoRateStatus() {
        return autoRateStatus;
    }

    public void setAutoRateStatus(boolean autoRateStatus) {
        this.autoRateStatus = autoRateStatus;
    }

    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    public TriggerMode getTriggerMode() {
        return triggerMode;
    }

    public void setTriggerMode(TriggerMode triggerMode) {
        this.triggerMode = triggerMode;
    }

    public boolean isAutoGrabRate() {
        return autoGrabRate;
    }

    public void setAutoGrabRate(boolean autoGrabRate) {
        this.autoGrabRate = autoGrabRate;
    }

    public boolean isMediumOrPoorRateAlarm() {
        return mediumOrPoorRateAlarm;
    }

    public void setMediumOrPoorRateAlarm(boolean mediumOrPoorRateAlarm) {
        this.mediumOrPoorRateAlarm = mediumOrPoorRateAlarm;
    }

    @Column
    @Enumerated(EnumType.STRING)       //自动评价类型
    private RateType rateType;
    @Column
    @Enumerated(EnumType.STRING)      //自动触发方式
    private TriggerMode triggerMode;
    @Column     //自动抢评
    private boolean autoGrabRate = true;
    @Column     //中评差评警告提醒 是否开启
    private boolean mediumOrPoorRateAlarm;

    public static void main(String[] args) {
        TriggerMode triggerMode1 = TriggerMode.BUYER_CONFIRM_RIGHT_AWAY_RATE ;
        System.out.println(triggerMode1.name());
    }

}
