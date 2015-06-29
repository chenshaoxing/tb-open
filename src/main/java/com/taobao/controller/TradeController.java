package com.taobao.controller;

import com.taobao.api.domain.Trade;
import com.taobao.common.ResultJson;
import com.taobao.common.Utils;
import com.taobao.service.TradeService;
import com.taobao.service.UserService;
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
    @Resource
    private UserService userService;

    @RequestMapping(value = "/trade/buyer-info")
    @ResponseBody
    public Map<String, Object> getBuyerInfo(@RequestParam Long tradeId) throws Exception{
        try{
            com.taobao.entity.User user = userService.findById(Utils.getUserId());
            Trade trade = tradeService.getTradeBuyerContactInfo(tradeId,user.getSessionKey());
            return ResultJson.resultSuccess(trade);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


}
