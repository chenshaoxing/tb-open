package com.taobao.service.impl;

import com.taobao.dao.IBasePersistence;
import com.taobao.entity.TbRelationJd;
import com.taobao.service.TbRelationJdService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-12
 * Time: 16:46
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TbRelationJdServiceImpl implements TbRelationJdService {
    @Resource
    private IBasePersistence basePersistence;

    @Override
    public TbRelationJd save(TbRelationJd tbRelationJd) {
        return basePersistence.save(tbRelationJd);
    }

    @Override
    public TbRelationJd findByTbIdAndJdId(Long tbId, Long jdId) {
        Map<String,Object> params = new HashMap();
        params.put("product.id",tbId);
        params.put("jdProduct.id",jdId);
        List<TbRelationJd> list = basePersistence.getEntitiesByFieldList(TbRelationJd.class,params);
        if(list != null && list.size()> 0){
            return list.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<TbRelationJd> findByTbNumId(Long numIid) {
        return  basePersistence.getEntitiesByField(TbRelationJd.class, "product.numIid",numIid);
    }

    @Override
    public List<TbRelationJd> findByJdSkuId(String skuId) {
        return  basePersistence.getEntitiesByField(TbRelationJd.class, "jdProduct.skuid",skuId);
    }
}
