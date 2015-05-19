package com.hr.system.manage.repository.dao;

/**
 * Created with IntelliJ IDEA.
 * User: csx
 * Date: 9/25/13
 * Time: 3:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrderBy {
    /*排序字段名称 默认updatedTime*/
    private String orderField = "updatedTime";
    /*排序类型 默认倒序*/
    private OrderType orderType = OrderType.desc;
    public enum OrderType{
        desc,asc
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
}
