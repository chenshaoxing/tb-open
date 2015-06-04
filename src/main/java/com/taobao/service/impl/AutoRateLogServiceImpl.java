package com.taobao.service.impl;

import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.dao.PageInfo;
import com.taobao.entity.AutoRateLog;
import com.taobao.service.AutoRateLogService;
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
 * Created by star on 15/5/23.
 */
@Service(value = "autoRateLogServiceImpl")
public class AutoRateLogServiceImpl implements AutoRateLogService{
    private static final Logger LOG = LoggerFactory.getLogger(AutoRateLogServiceImpl.class);
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public AutoRateLog add(AutoRateLog autoRateLog) {
        return iBasePersistence.save(autoRateLog);
    }

    @Override
    public PageInfo<AutoRateLog> getList(int currentPage, int pageSize, Map<String, Object> params) {
        List<Expression> list = new ArrayList<Expression>();
        if(params.get("tradeId") != null){
            Expression tradeId = new Expression("tid",params.get("tradeId"),Expression.Relation.AND, Expression.Operation.Equal);
            list.add(tradeId);
        }
        if(params.get("buyerNick") != null){
            Expression buyer = new Expression("buyerNickname",params.get("buyerNick"), Expression.Relation.AND, Expression.Operation.Equal);
            list.add(buyer);
        }
        if(params.get("startTime") != null){
            String startTime = params.get("startTime").toString();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Expression startExp = new Expression("rateTime",sf.parse(startTime), Expression.Relation.AND, Expression.Operation.GreaterThanEqual);
                list.add(startExp);
            } catch (ParseException e) {
                LOG.error(e.getMessage());
            }
        }
        if(params.get("endTime") != null){
            String endTime = params.get("endTime").toString();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                ;
                Expression endExp = new Expression("rateTime",sf.parse(endTime), Expression.Relation.AND, Expression.Operation.LessThanEqual);
                list.add(endExp);
            } catch (ParseException e) {
                LOG.error(e.getMessage());
            }
        }
        if(params.get("userId") != null){
             Expression userExp = new Expression("user.id",params.get("userId"), Expression.Relation.AND, Expression.Operation.Equal);
            list.add(userExp);
        }
        return iBasePersistence.getEntitiesByExpressions(AutoRateLog.class,list,"id",currentPage,pageSize);
    }

    @Override
    public void remove(AutoRateLog autoRateLog) {
        iBasePersistence.removeEntity(autoRateLog);
    }
}
