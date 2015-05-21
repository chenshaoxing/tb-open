package com.taobao.controller;

import com.hr.system.manage.common.ResultJson;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.User;
import com.taobao.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-21
 * Time: 10:29
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class TradeController {
    private static final Logger LOG = LoggerFactory.getLogger(TradeController.class);

    @Resource
    private TradeService tradeService;

    @RequestMapping(value = "/trade/buyer-info")
    @ResponseBody
    public Map<String, Object> getBuyerInfo(@RequestParam Long tradeId) throws Exception{
        try{
            Trade trade = tradeService.getTradeBuyerContactInfo(tradeId);
            return ResultJson.resultSuccess(trade);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
