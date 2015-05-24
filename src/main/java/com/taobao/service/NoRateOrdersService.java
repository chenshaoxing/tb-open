package com.taobao.service;

import com.taobao.entity.NoRateOrders;

import java.util.List;
import java.util.Map;

/**
 * Created by star on 15/5/24.
 */
public interface NoRateOrdersService {
    public NoRateOrders add(NoRateOrders no);

    public List<NoRateOrders> getList(Map<String,Object> dateParams);

    public NoRateOrders findByTradeId(Long tradeId);
}
