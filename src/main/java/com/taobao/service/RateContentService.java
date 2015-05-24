package com.taobao.service;

import com.taobao.entity.RateContent;

import java.util.List;

/**
 * Created by star on 15/5/22.
 */
public interface RateContentService {
    public RateContent add(RateContent rateContent);

    public List<RateContent> findBySettingId(Long id);
}
