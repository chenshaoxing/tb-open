package com.taobao.dao;

import java.util.List;


public class PageInfo<T> {
    //TODO 代码重构
    /**
     * 总页数
     */
    private long pageTotalNum;

    /**
     * 数据列表
     */
    private List<T> list;

    /**
     * 数据总记录数
     */
    private long recordTotalCount;

    /**
     * 用于hbase分页
     */
    private String nextKey;


    public PageInfo(long pageSize, long recordTotalCount){
        this.pageTotalNum = (recordTotalCount + pageSize - 1) / pageSize;
        this.recordTotalCount = recordTotalCount;
    }

    public PageInfo() {
    }

    public long getPageTotalNum() {
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

    public long getRecordTotalCount() {
        return recordTotalCount;
    }

    public void setRecordTotalCount(long recordTotalCount) {
        this.recordTotalCount = recordTotalCount;
    }

    public String getNextKey() {
        return nextKey;
    }

    public void setNextKey(String nextKey) {
        this.nextKey = nextKey;
    }
}
