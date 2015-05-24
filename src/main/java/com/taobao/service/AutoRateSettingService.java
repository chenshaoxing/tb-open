package com.taobao.service;

import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.User;

/**
 * Created by star on 15/5/22.
 */
public interface AutoRateSettingService {
    public AutoRateSetting add(AutoRateSetting autoRateSetting);

    public AutoRateSetting findByUser(User user);

    public AutoRateSetting findById(Long id);
}
