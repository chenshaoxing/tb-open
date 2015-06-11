package com.taobao.service;


import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 15/5/12.
 */
public interface JDProductService {
    public JDProduct save(Map<String, Object> objectMap);

    public JDProduct save(JDProduct jdProduct);

    public List<JDProduct> getList();

    public JDProduct findBySkuId(String skuId);

    public PageInfo<JDProduct> getList(int currentPage, int pageSize);
}
