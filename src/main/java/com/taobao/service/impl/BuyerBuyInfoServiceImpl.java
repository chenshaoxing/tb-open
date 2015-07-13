package com.taobao.service.impl;

import com.taobao.api.domain.Location;
import com.taobao.api.domain.Order;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import com.taobao.dao.IBasePersistence;
import com.taobao.entity.BuyerBuyInfo;
import com.taobao.entity.TaoBaoProduct;
import com.taobao.entity.User;
import com.taobao.service.BuyerBuyInfoService;
import com.taobao.service.TaoBaoProductService;
import com.taobao.service.TradeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by star on 15/7/8.
 */
@Service(value = "buyerBuyInfoService")
public class BuyerBuyInfoServiceImpl implements BuyerBuyInfoService {
    @Resource
    private IBasePersistence basePersistence;
    @Resource
    private TaoBaoProductService taoBaoProductService;
    @Resource
    private TradeService tradeService;


    @Override
    public void add(Shipping shipping,User user) throws Exception{
        Location location = shipping.getLocation();
        Trade trade = tradeService.getTradeInfo(shipping.getTid(), user.getSessionKey());
        List<Order> orders = trade.getOrders();
        for(Order order:orders){
            BuyerBuyInfo buyerBuyInfo = new BuyerBuyInfo();
            buyerBuyInfo.setItemTitle(order.getTitle());
            buyerBuyInfo.setReceiverMobile(shipping.getReceiverMobile());
            buyerBuyInfo.setReceiverName(shipping.getReceiverName());
            buyerBuyInfo.setBuyerNick(shipping.getBuyerNick());
            buyerBuyInfo.setAddress(location.getState()+" "+location.getCity()+" "+ location.getDistrict()+" "+location.getAddress());
            buyerBuyInfo.setNumIid(order.getNumIid());
            buyerBuyInfo.setNum(order.getNum());
            buyerBuyInfo.setOid(order.getOid());
            buyerBuyInfo.setTid(shipping.getTid());
            basePersistence.save(buyerBuyInfo);
        }
    }

    @Override
    public BuyerBuyInfo add(BuyerBuyInfo buyerBuyInfo) {
        return basePersistence.save(buyerBuyInfo);
    }
}
