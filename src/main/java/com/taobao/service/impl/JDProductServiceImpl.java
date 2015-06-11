package com.taobao.service.impl;


import com.taobao.common.Constants;
import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;
import com.taobao.service.JDProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 15/5/12.
 */
@Service(value ="JDProductServiceImpl")
public class JDProductServiceImpl implements JDProductService {
    @Resource
    private IBasePersistence basePersistence;

    @Override
    public JDProduct save(Map<String,Object> productObj) {
        return basePersistence.save(jsonObjChangeJDProduct(productObj));
    }

    @Override
    public JDProduct save(JDProduct jdProduct) {
        return basePersistence.save(jdProduct);
    }


    @Override
    public List<JDProduct> getList() {
        return basePersistence.getAllEntities(JDProduct.class);
    }

    @Override
    public JDProduct findBySkuId(String skuId) {
        return basePersistence.getEntityByField(JDProduct.class,"skuId",skuId);
    }


    @Override
    public PageInfo<JDProduct> getList(int currentPage, int pageSize) {
        List<Expression> list = new ArrayList<Expression>();
        return basePersistence.getEntitiesByExpressions(JDProduct.class,list,"id",currentPage,pageSize);
    }


    private JDProduct jsonObjChangeJDProduct(Map<String,Object> master){
        JDProduct product = new JDProduct();
        product.setCrawlUrl(master.get("crawlUrl").toString());
        product.setDiscount(Float.valueOf(master.get("discount").toString()));
        product.setPrice(Float.valueOf(master.get("price").toString()));
        product.setName(master.get("name")+"");
        product.setUrl(Constants.JD_PRODUCT_ITEM_PREFIX+master.get("skuid")+ Constants.JD_PRODUCT_ITEM_END_PREFIX);
        product.setSkuid(master.get("skuid")+"");
        product.setPic(Constants.JD_PRODUCT_INFO_IMAGE_URL_PREFIX+master.get("pic"));
        return product;
    }
}
