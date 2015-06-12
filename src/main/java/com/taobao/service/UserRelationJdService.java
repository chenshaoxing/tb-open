package com.taobao.service;

import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;
import com.taobao.entity.UserRelationJd;

/**
 * Created by Administrator on 2015-06-12.
 */
public interface UserRelationJdService {
    public UserRelationJd add(UserRelationJd uj);

    public UserRelationJd findById(UserRelationJd id);

    public void delete(UserRelationJd userRelationJd);

    public UserRelationJd findBySkuAndUserId(UserRelationJd userRelationJd);


    public PageInfo<UserRelationJd> getList(int currentPage, int pageSize,String name,String skuId,Long userId);
}
