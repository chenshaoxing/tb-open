package com.taobao.entity;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by star on 15/5/7.
 */
@Entity
@Table(name = "t_jd_product")
public class JDProduct extends BaseDomain {

    @Column                //商品最新价格
    private Float price;
    @Column               //商品名称
    private String name;
    @Column               //商品完整的URL
    private String url;
    @Column               //商品的主图片URL
    private String pic;
    @Column               //商品的id
    private String skuid;
    @Column              //商品的最新折扣

    public Float getDiscount() {
        return discount;
    } public void setDiscount(Float discount) {
        this.discount = discount;
    } private Float discount;

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Column         //抓取商品信息的url

    public String getCrawlUrl() {
        return crawlUrl;
    } public void setCrawlUrl(String crawlUrl) {
        this.crawlUrl = crawlUrl;
    } public String getSkuid() {
        return skuid;
    } public void setSkuid(String skuid) {
        this.skuid = skuid;
    } public String getPic() {
        return pic;
    } public void setPic(String pic) {
        this.pic = pic;
    } public String getUrl() {
        return url;
    } public void setUrl(String url) {
        this.url = url;
    } public String getName() {
        return name;
    } public void setName(String name) {
        this.name = name;
    } private String crawlUrl;

}
