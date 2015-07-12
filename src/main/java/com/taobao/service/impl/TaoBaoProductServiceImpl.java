package com.taobao.service.impl;

import com.taobao.dao.IBasePersistence;
import com.taobao.entity.JDProduct;
import com.taobao.entity.TaoBaoProduct;
import com.taobao.entity.TbRelationJd;
import com.taobao.service.TaoBaoProductService;
import com.taobao.service.TbRelationJdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-08
 * Time: 15:28
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "taoBaoProductServiceImpl")
public class TaoBaoProductServiceImpl implements TaoBaoProductService {
    @Resource
    private IBasePersistence basePersistence;

    @Resource
    private TbRelationJdService tbRelationJdService;


    @Override
    public void update(TaoBaoProduct product) {
        basePersistence.update(product);
    }

    @Override
    public TaoBaoProduct add(TaoBaoProduct product) {
        return basePersistence.save(product);
    }

    @Override
    @Transactional
    public boolean addAndRelationJd(TaoBaoProduct tb, JDProduct jd) {
        tb = basePersistence.save(tb);
        TbRelationJd tbRelationJd = tbRelationJdService.findByTbIdAndJdId(tb.getId(),jd.getId());
        if(tbRelationJd == null){
            tbRelationJd = new TbRelationJd();
            tbRelationJd.setJdProduct(jd);
            tbRelationJd.setProduct(tb);
            basePersistence.save(tbRelationJd);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<TaoBaoProduct> findAll() {
        return basePersistence.getAllEntities(TaoBaoProduct.class);
    }

    @Override
    public TaoBaoProduct findById(Long id) {
        TaoBaoProduct product = new TaoBaoProduct();
        product.setId(id);
        return basePersistence.getEntityById(TaoBaoProduct.class,product);
    }

    @Override
    public List<TaoBaoProduct> findByNumIid(Long numIid) {
        return basePersistence.getEntitiesByField(TaoBaoProduct.class, "numIid", numIid, "id", Integer.MAX_VALUE);
    }
}
