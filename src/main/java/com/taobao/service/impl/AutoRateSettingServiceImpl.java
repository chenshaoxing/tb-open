package com.taobao.service.impl;

import com.taobao.dao.IBasePersistence;
import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.User;
import com.taobao.service.AutoRateSettingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by star on 15/5/22.
 */
@Service
public class AutoRateSettingServiceImpl implements AutoRateSettingService {
    @Resource
    private IBasePersistence iBasePersistence;

    @Override
    public AutoRateSetting add(AutoRateSetting autoRateSetting) {
        return iBasePersistence.save(autoRateSetting);
    }

    @Override
    public AutoRateSetting findByUser(User user) {
        return iBasePersistence.getEntityByField(AutoRateSetting.class,"user.id",user.getId());
    }

    @Override
    public AutoRateSetting findById(Long id) {
        return iBasePersistence.getEntityByField(AutoRateSetting.class,"id",id);
    }
}
