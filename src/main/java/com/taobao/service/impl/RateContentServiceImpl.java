package com.taobao.service.impl;

import com.taobao.dao.IBasePersistence;
import com.taobao.entity.RateContent;
import com.taobao.service.RateContentService;
import org.apache.poi.ss.formula.functions.Rate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by star on 15/5/22.
 */
@Service(value = "rateContentServiceImpl")
public class RateContentServiceImpl implements RateContentService{
    @Resource
    private IBasePersistence iBasePersistence;
    @Override
    public RateContent add(RateContent rateContent) {
        return iBasePersistence.save(rateContent);
    }

    @Override
    public List<RateContent> findBySettingId(Long id) {
        return iBasePersistence.getEntitiesByField(RateContent.class,"autoRateSetting.id",id);
    }

    @Override
    public RateContent findById(Long id) {
        RateContent rateContent = new RateContent();
        rateContent.setId(id);
        return iBasePersistence.getEntityById(RateContent.class,rateContent);
    }
}
