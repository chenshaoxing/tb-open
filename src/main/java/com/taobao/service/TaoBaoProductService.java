package com.taobao.service;


import com.taobao.entity.TaoBaoProduct;

import java.util.List;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-08
 * Time: 15:28
 * function:
 * To change this template use File | Settings | File Templates.
 */
public interface TaoBaoProductService {
    public TaoBaoProduct add(TaoBaoProduct product);

    public List<TaoBaoProduct> findAll();

    public TaoBaoProduct findById(Long id);

    public TaoBaoProduct findByNumIid(Long numIid);
}
