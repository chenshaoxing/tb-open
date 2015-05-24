package com.taobao.service.impl;

import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.entity.NoRateOrders;
import com.taobao.service.NoRateOrdersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by star on 15/5/24.
 */
@Service
public class NoRateOrdersServiceImpl implements NoRateOrdersService {
    private static final Logger LOG = LoggerFactory.getLogger(NoRateOrdersServiceImpl.class);

    @Resource
    private IBasePersistence basePersistence;

    @Override
    public NoRateOrders add(NoRateOrders no) {
        return basePersistence.save(no);
    }

    @Override
    public List<NoRateOrders> getList(Map<String, Object> dateParams) {
        List<Expression> list = new ArrayList<Expression>();
        if(dateParams.get("startTime") != null){
            String startTime = dateParams.get("startTime").toString();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                ;
                Expression startExp = new Expression("overTime",sf.parse(startTime), Expression.Relation.AND, Expression.Operation.GreaterThanEqual);
                list.add(startExp);
            } catch (ParseException e) {
                LOG.error(e.getMessage());
            }
        }
        if(dateParams.get("endTime") != null){
            String endTime = dateParams.get("endTime").toString();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                ;
                Expression endExp = new Expression("overTime",sf.parse(endTime), Expression.Relation.AND, Expression.Operation.LessThanEqual);
                list.add(endExp);
            } catch (ParseException e) {
                LOG.error(e.getMessage());
            }
        }
        return basePersistence.getEntitiesByExpressions(NoRateOrders.class,list,"id",1,Integer.MAX_VALUE).getList();
    }

    @Override
    public NoRateOrders findByTradeId(Long tradeId) {
        return basePersistence.getEntityByField(NoRateOrders.class,"tid",tradeId);
    }
}
