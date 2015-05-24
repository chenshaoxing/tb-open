package com.taobao.service;

import com.taobao.dao.PageInfo;
import com.taobao.entity.AutoRateLog;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 15/5/23.
 */
public interface AutoRateLogService {

    public AutoRateLog add(AutoRateLog autoRateLog);


    public PageInfo<AutoRateLog> getList(int currentPage,int pageSize,Map<String,Object> params);
}
