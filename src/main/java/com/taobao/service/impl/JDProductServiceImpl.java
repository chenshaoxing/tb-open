package com.taobao.service.impl;


import com.taobao.common.Constants;
import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;
import com.taobao.service.JDProductService;
import org.apache.commons.lang.StringUtils;
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
        JDProduct product = jsonObjChangeJDProduct(productObj);
        JDProduct temp = this.findBySkuId(product.getSkuid());
        if(temp == null){
            return basePersistence.save(product);
        }else{
            product.setId(temp.getId());
            return  basePersistence.save(product);
        }
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
        return basePersistence.getEntityByField(JDProduct.class,"skuid",skuId);
    }

    @Override
    public JDProduct findById(Long id) {
        JDProduct jdProduct = new JDProduct();
        jdProduct.setId(id);
        return basePersistence.getEntityById(JDProduct.class,jdProduct);
    }


    @Override
    public PageInfo<JDProduct> getList(int currentPage, int pageSize,String name,String skuId) {
        List<Expression> list = new ArrayList<Expression>();
        if(StringUtils.isNotEmpty(name)){
            Expression nameExp = new Expression("name",name, Expression.Relation.AND, Expression.Operation.AllLike);
            list.add(nameExp);
        }
        if(StringUtils.isNotEmpty(skuId)){
            Expression skuExp = new Expression("skuid",skuId, Expression.Relation.AND, Expression.Operation.Equal);
            list.add(skuExp);
        }
        return basePersistence.getEntitiesByExpressions(JDProduct.class,list,"id",currentPage,pageSize);
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
