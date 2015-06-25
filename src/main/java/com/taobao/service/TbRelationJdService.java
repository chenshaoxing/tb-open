package com.taobao.service;

import com.taobao.entity.TbRelationJd;

import java.util.List;

/**
 * Created by Administrator on 2015-06-12.
 */
public interface TbRelationJdService {
    public TbRelationJd save(TbRelationJd tbRelationJd);

    public TbRelationJd findByTbIdAndJdId(Long numIid,Long jdId);

    public List<TbRelationJd> findByTbNumId(Long numIid);

    public List<TbRelationJd> findByJdSkuId(String skuId);

    public void remove(TbRelationJd tj);
}
