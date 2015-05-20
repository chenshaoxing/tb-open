package com.taobao.controller;

import com.hr.system.manage.common.ResultJson;
import com.hr.system.manage.repository.dao.PageInfo;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.common.Utils;
import com.taobao.service.ProductService;
import com.taobao.service.RateService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-20
 * Time: 14:30
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class RateController {
    public static final Logger LOG = LoggerFactory.getLogger(RateController.class);

    @Resource
    private ProductService productService;
    @Resource
    private RateService rateService;



    @RequestMapping(value = "/rate/already-rate-orders")
    public String alreadyRateOrderIndex(){
        return "rate/already-rate-order";
    }

    @RequestMapping(value = "/rate/already-bad-rate-orders")
    public String alreadyBadRateOrderIndex(){
        return "rate/already-bad-rate-order";
    }

    @RequestMapping(value = "/rate/already-neutral-rate-orders")
    public String alreadyNeutralRateOrderIndex(){
        return "rate/already-neutral-rate-order";
    }

    @RequestMapping(value = "/rate/already-rate-orders/list")
    @ResponseBody
    public Map<String, Object> getAlreadyRateOrders(@RequestParam Long currentPage,
                                     @RequestParam Long pageSize,
                                     @RequestParam(required = false) Long tradeId,
                                     @RequestParam(required = false) String result,
                                     @RequestParam(required = false) boolean isBuyerGiveMe
                                     ) throws Exception{
        try{
            TraderatesGetRequest request = new TraderatesGetRequest();
            if(tradeId != null){
                request.setTid(tradeId);
            }

            request.setPageNo(currentPage);
            request.setPageSize(pageSize);

            if(isBuyerGiveMe){
                request.setRateType("get");
                request.setRole("buyer");
            }else {
                request.setRateType("give");
                request.setRole("seller");
            }

            if(StringUtils.isNotEmpty(result)){
                request.setResult(result);
            }
            TraderatesGetResponse response = rateService.searchRate(request);
            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>() ;
            for(TradeRate tradeRate :response.getTradeRates()) {
                Map<String,Object> map = Utils.toMap(tradeRate);
                Item item = productService.getProductByNumId(tradeRate.getNumIid());
                if(item != null){
                    map.put("productUrl",item.getDetailUrl());
                }
                list.add(map);
            }
            PageInfo pageInfo = new PageInfo(pageSize,response.getTotalResults());
            pageInfo.setList(list);
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
