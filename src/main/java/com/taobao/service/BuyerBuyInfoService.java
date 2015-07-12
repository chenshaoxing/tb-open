package com.taobao.service;

import com.taobao.api.domain.Shipping;
import com.taobao.entity.BuyerBuyInfo;
import com.taobao.entity.User;

/**
 * Created by star on 15/7/8.
 */
public interface BuyerBuyInfoService {
    public BuyerBuyInfo add(Shipping shipping,User user)  throws Exception;
    public BuyerBuyInfo add(BuyerBuyInfo buyerBuyInfo);
}
