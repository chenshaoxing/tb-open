package com.taobao.service.impl;

import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;
import com.taobao.entity.UserRelationJd;
import com.taobao.service.UserRelationJdService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-12
 * Time: 10:36
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserRelationJdServiceImpl implements UserRelationJdService {
    @Resource
    private IBasePersistence persistence;

    @Override
    public UserRelationJd add(UserRelationJd uj) {
        UserRelationJd u = this.findBySkuAndUserId(uj);
        if(u == null){
            return persistence.save(uj);
        }else{
            return u;
        }
    }

    @Override
    public UserRelationJd findById(UserRelationJd jd) {
        return persistence.getEntityById(UserRelationJd.class,jd);
    }

    @Override
    public void delete(UserRelationJd userRelationJd) {
        persistence.removeDetachedEntity(userRelationJd);
    }

    @Override
    public UserRelationJd findBySkuAndUserId(UserRelationJd userRelationJd) {
        Map<String,Object> params = new HashMap();
        params.put("user.id",userRelationJd.getUser().getId());
        params.put("product.id",userRelationJd.getProduct().getId());
        List<UserRelationJd> list =  persistence.getEntitiesByFieldList(UserRelationJd.class,params);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<UserRelationJd> getList(int currentPage, int pageSize, String name, String skuId, Long userId) {
        List<Expression> list = new ArrayList<Expression>();
        if(userId != null){
            Expression userIdExp = new Expression("user.id",userId, Expression.Relation.AND, Expression.Operation.Equal);
            list.add(userIdExp);
        }
        if(StringUtils.isNotEmpty(name)){
            Expression nameExp = new Expression("product.name",name, Expression.Relation.AND, Expression.Operation.AllLike);
            list.add(nameExp);
        }
        if(StringUtils.isNotEmpty(skuId)){
            Expression skuExp = new Expression("product.skuid",skuId, Expression.Relation.AND, Expression.Operation.Equal);
            list.add(skuExp);
        }
        return persistence.getEntitiesByExpressions(UserRelationJd.class,list,"id",currentPage,pageSize);
    }
}
