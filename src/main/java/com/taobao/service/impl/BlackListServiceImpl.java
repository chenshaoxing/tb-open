package com.taobao.service.impl;

import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.dao.PageInfo;
import com.taobao.entity.BlackList;
import com.taobao.service.BlackListService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 15/5/23.
 */
@Service(value = "blackListServiceImpl")
public class BlackListServiceImpl implements BlackListService{
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public BlackList add(BlackList blackList) {
        return iBasePersistence.save(blackList);
    }

    @Override
    public void delete(Long id) {
        BlackList blackList = new BlackList();
        blackList.setId(id);
        iBasePersistence.removeEntity(blackList);
    }

    @Override
    public PageInfo<BlackList> getList(int currentPage, int pageSize, String buyerNick,Long userId) {
        List<Expression> list = new ArrayList<Expression>() ;
        if(StringUtils.isNotEmpty(buyerNick)){
            Expression expression = new Expression("buyerNickname",buyerNick, Expression.Relation.AND, Expression.Operation.AllLike);
            list.add(expression);
        }
        if(userId != null){
            Expression userIdExp = new Expression("user.id",userId, Expression.Relation.AND, Expression.Operation.Equal);
            list.add(userIdExp);
        }
        return iBasePersistence.getEntitiesByExpressions(BlackList.class,list,"id",currentPage,pageSize);
    }

    @Override
    public BlackList findByName(String buyerNick) {
        return iBasePersistence.getEntityByField(BlackList.class,"buyerNickname",buyerNick);
    }
}
