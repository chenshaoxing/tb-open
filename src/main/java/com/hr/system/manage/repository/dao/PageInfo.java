package com.hr.system.manage.repository.dao;

import java.util.List;

/**
 * 用于返回分页查询的结果，现阶段hbase无法返回查询的总页数，因此pageTotalNum和recordTotalCount
 * 只用于RDBMS数据库的分页，hbase分页使用nextKey属性，即下一页的第一条记录的rowkey
 *
 * User: Sneaker
 * Date: 12-12-13
 * Time: 下午7:30
 */
public class PageInfo<T> {
    //TODO 代码重构
    /**
     * 总页数
     */
    private int pageTotalNum;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 数据总记录数
     */
    private int recordTotalCount;

    /**
     * 用于hbase分页
     */
    private String nextKey;


    public PageInfo(int pageSize, int recordTotalCount){
        this.pageTotalNum = (recordTotalCount + pageSize - 1) / pageSize;
        this.recordTotalCount = recordTotalCount;
    }

    public PageInfo() {
    }

    public int getPageTotalNum() {
        return pageTotalNum;
    }

    public void setPageTotalNum(int pageTotalNum) {
        this.pageTotalNum = pageTotalNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getRecordTotalCount() {
        return recordTotalCount;
    }

    public void setRecordTotalCount(int recordTotalCount) {
        this.recordTotalCount = recordTotalCount;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
