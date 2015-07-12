package com.taobao.service.impl;

import com.taobao.dao.Expression;
import com.taobao.dao.IBasePersistence;
import com.taobao.dao.PageInfo;
import com.taobao.entity.ShipperNotifyDelay;
import com.taobao.service.ShipperNotifyDelayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by star on 15/7/12.
 */
@Service
public class ShipperNotifyDelayServiceImpl implements ShipperNotifyDelayService {
    @Resource
    private IBasePersistence basePersistence;

    @Override
    public ShipperNotifyDelay save(ShipperNotifyDelay sp) {
        return basePersistence.save(sp);
    }

    @Override
    public PageInfo<ShipperNotifyDelay> getList(int currentPage, int pageSize) {
        List<Expression> list = new ArrayList<Expression>();
        return basePersistence.getEntitiesByExpressions(ShipperNotifyDelay.class,list,"id",currentPage,pageSize);
    }

    @Override
    public List<ShipperNotifyDelay> successList() {
        return basePersistence.getEntitiesByField(ShipperNotifyDelay.class,"isSuccess",true,Integer.MAX_VALUE);
    }

    @Override
    public void delete(ShipperNotifyDelay sp) {
        basePersistence.removeEntity(sp);
    }
}
