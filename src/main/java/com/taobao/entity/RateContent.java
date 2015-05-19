package com.taobao.entity;

import javax.persistence.*;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-19
 * Time: 17:22
 * function:
 * 评价内容定义
 */
@Entity
@Table(name = "t_rate_content")
public class RateContent extends BaseDomain{
    public AutoRateSetting getAutoRateSetting() {
        return autoRateSetting;
    }

    public void setAutoRateSetting(AutoRateSetting autoRateSetting) {
        this.autoRateSetting = autoRateSetting;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(cascade = {CascadeType.REFRESH},optional = true,fetch = FetchType.EAGER)
    @JoinColumn(name="auto_rate_setting_id")
    private AutoRateSetting autoRateSetting;

    @Column(length = 10000)
    private String content;
}
