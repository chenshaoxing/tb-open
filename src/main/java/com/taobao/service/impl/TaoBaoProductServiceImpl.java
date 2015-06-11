package com.taobao.service.impl;

import com.taobao.dao.IBasePersistence;
import com.taobao.entity.TaoBaoProduct;
import com.taobao.service.TaoBaoProductService;
import org.springframework.stereotype.Service;

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

    @Override
    public TaoBaoProduct add(TaoBaoProduct product) {
        return basePersistence.save(product);
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
    public TaoBaoProduct findByNumIid(Long numIid) {
        return basePersistence.getEntityByField(TaoBaoProduct.class,"numIid",numIid);
    }
}
