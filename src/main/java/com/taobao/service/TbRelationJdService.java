package com.taobao.service;

import com.taobao.entity.TbRelationJd;

/**
 * Created by Administrator on 2015-06-12.
 */
public interface TbRelationJdService {
    public TbRelationJd save(TbRelationJd tbRelationJd);

    public TbRelationJd findByTbIdAndJdId(Long tbId,Long jdId);
}
