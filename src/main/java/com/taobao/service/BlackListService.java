package com.taobao.service;

import com.taobao.dao.PageInfo;
import com.taobao.entity.BlackList;

import java.util.Map;

/**
 * Created by star on 15/5/23.
 */
public interface BlackListService {
    public BlackList add(BlackList blackList);

    public void delete(Long id);

    public PageInfo<BlackList> getList(int currentPage,int pageSize,String buyerNick);

    public BlackList findByName(String buyerNick);

}
