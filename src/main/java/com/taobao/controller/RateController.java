package com.taobao.controller;

import com.hr.system.manage.common.ResultJson;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.Trade;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.common.Utils;
import com.taobao.dao.PageInfo;
import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.RateContent;
import com.taobao.entity.RateStatus;
import com.taobao.entity.User;
import com.taobao.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.Rate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

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
    private static final Logger LOG = LoggerFactory.getLogger(RateController.class);

    @Resource
    private ProductService productService;
    @Resource
    private RateService rateService;
    @Resource
    private TradeService tradeService;
    @Resource
    private UserService userService;
    @Resource
    private AutoRateSettingService autoRateSettingService;
    @Resource
    private RateContentService rateContentService;



    @RequestMapping(value = "/rate/already-rate-orders")
    public String alreadyRateOrderIndex(){
        return "rate/already-rate-order";
    }

    @RequestMapping(value = "/rate/batch-rate-orders")
    public String batchRateOrderIndex(){
        return "rate/batch-rate-order";
    }

    @RequestMapping(value = "/rate/rate-global-setting")
    public String rateGlobalSettingIndex(){
        return "rate/rate-global-setting";
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
//            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>() ;
//            for(TradeRate tradeRate :response.getTradeRates()) {
//                Map<String,Object> map = Utils.toMap(tradeRate);
//                Item item = productService.getProductByNumId(tradeRate.getNumIid());
//                if(item != null){
//                    map.put("productUrl",item.getDetailUrl());
//                }
//                list.add(map);
//            }
            PageInfo pageInfo = new PageInfo(pageSize,response.getTotalResults());
//            pageInfo.setList(list);
            pageInfo.setList(response.getTradeRates());
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    @RequestMapping(value = "/rate/batch-rate-orders/list")
    @ResponseBody
    public Map<String, Object> getBatchRateOrders(@RequestParam Long currentPage,
                                                    @RequestParam Long pageSize,
                                                    @RequestParam(required = false) String buyerNick,
                                                    @RequestParam(required = false) String rateStatus
    ) throws Exception{
        try{
            TradesSoldGetRequest request = new TradesSoldGetRequest();
            request.setPageNo(currentPage);
            request.setPageSize(pageSize);

            if(StringUtils.isNotEmpty(buyerNick)){
                request.setBuyerNick(buyerNick);
            }
            request.setRateStatus(rateStatus);
            TradesSoldGetResponse response = tradeService.getTradeSold(request);
            PageInfo pageInfo = new PageInfo(pageSize,response.getTotalResults());
            List<Map<String,Object>> list = new ArrayList<Map<String, Object>>() ;
            if(response.getTrades() != null && response.getTrades().size() > 0){
                for(Trade trade :response.getTrades()) {
                    Map<String,Object> map = Utils.toMap(trade);
                    Date endTime = trade.getEndTime();
                    Calendar cal =  Calendar.getInstance();
                    cal.setTime(endTime);
                    cal.add(Calendar.DATE,15);
                    Long currentTimeMillis = System.currentTimeMillis();
                    if(cal.getTimeInMillis() < currentTimeMillis){
                        map.put("overTime","false");
                    }else{
                        Long result = cal.getTimeInMillis()-System.currentTimeMillis();
                        String overTime = Utils.formatTime(result);
                        map.put("overTime",overTime);
                    }
                    list.add(map);
                }
            }
            pageInfo.setList(list);
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/rate/batch-rate-orders/rate")
    @ResponseBody
    public Map<String, Object> rateBatchOrders(@RequestParam(required = false) String tradeIds,
                                               @RequestParam String content,
                                               @RequestParam String rateType,
                                               @RequestParam(required = false) String buyerNick,
                                               @RequestParam(required = false) String rateStatus
                                               ) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        List<Object> trades = new ArrayList<Object>();
        try{
            int success = 0;
            int failed = 0;
            if(StringUtils.isNotEmpty(tradeIds)){
                String ids []= tradeIds.split(",");
                for(String tradeId:ids){
                    boolean flag = rateService.add(Long.valueOf(tradeId), rateType.toLowerCase(),content);
                    if(flag)
                        success+=1;
                    else {
                        failed += 1;
                        trades.add(tradeId);
                    }
                }
            }else{
                Map<String,Object> result = this.getBatchRateOrders(1l,10l,buyerNick,rateStatus);
                PageInfo pageInfo  = (PageInfo)result.get("data");
                Long totalPage =  pageInfo.getPageTotalNum();
                for(long i = 1l;i<totalPage+1l;i++){
                    result = this.getBatchRateOrders(i,5l,buyerNick,rateStatus);
                    pageInfo  = (PageInfo)result.get("data");
                    totalPage = pageInfo.getPageTotalNum();
                    List<Map<String,Object>> list = pageInfo.getList();
                    for(Map<String,Object> param:list){
                        boolean flag = rateService.add(Long.valueOf(param.get("tid").toString()), rateType.toLowerCase(),content);
                        if(flag)
                            success+=1;
                        else {
                            failed += 1;
                            trades.add(param.get("tid").toString());
                        }
                    }
                }
            }
            map.put("tradeIds",trades);
            map.put("success",success);
            map.put("failed",failed);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
        return ResultJson.resultSuccess(map);
    }

    @RequestMapping(value = "/rate/auto-rate-global-setting")
    @ResponseBody
    public Map<String, Object> autoRateGlobalSetting(@RequestParam boolean autoRateStatus,
                                                     @RequestParam Long settingId,
                                                     @RequestParam boolean mediumOrPoorRateAlarm,
                                                     @RequestParam String autoRateType,
                                                     @RequestParam Long userId,
                                                     @RequestParam String email,
                                                     @RequestParam String triggerMode,
                                                     @RequestParam String content1,
                                                     @RequestParam String content2,
                                                     @RequestParam String content3
                                                     )throws Exception{
        try{
            User user = userService.findById(userId);
            user.setEmail(email);
            userService.add(user);
            AutoRateSetting setting = null;
            if(settingId != null && settingId > 0L){
                setting = autoRateSettingService.findById(settingId);
            }else{
                setting = new AutoRateSetting();
            }
            setting.setAutoRateStatus(autoRateStatus);
            setting.setUser(user);
            setting.setRateType(AutoRateSetting.RateType.valueOf(autoRateType));
            setting.setMediumOrPoorRateAlarm(mediumOrPoorRateAlarm);
            setting.setTriggerMode(AutoRateSetting.TriggerMode.valueOf(triggerMode));
            setting = autoRateSettingService.add(setting);
            RateContent rateContent1 = null;
            RateContent rateContent2 = null;
            RateContent rateContent3 = null;
            if(settingId != null && settingId > 0L){
                List<RateContent> contents = rateContentService.findBySettingId(setting.getId());
                rateContent1 =  contents.get(0) ;
                rateContent2 = contents.get(1) ;
                rateContent3 = contents.get(2) ;
            }else{
                rateContent1 = new RateContent();
                rateContent1.setAutoRateSetting(setting);
                rateContent2 = new RateContent();
                rateContent2.setAutoRateSetting(setting);
                rateContent3 = new RateContent();
                rateContent3.setAutoRateSetting(setting);
            }
            rateContent1.setContent(content1);
            rateContent2.setContent(content2);
            rateContent3.setContent(content3);
            rateContentService.add(rateContent1);
            rateContentService.add(rateContent2);
            rateContentService.add(rateContent3);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    @RequestMapping(value = "/rate/auto-rate-global-setting/init")
    @ResponseBody
    public Map<String, Object> initAutoRateGlobalSetting(@RequestParam Long userId) throws Exception{
        try{
            User user = new User();
            user.setId(userId);
            AutoRateSetting setting = autoRateSettingService.findByUser(user);
            List<RateContent> contents = new ArrayList<RateContent>();
            if(setting != null){
                contents  = rateContentService.findBySettingId(setting.getId());
            }
            Map<String ,Object> result = new HashMap<String, Object>();
            result.put("setting",setting);
            result.put("contents",contents);
            return ResultJson.resultSuccess(result);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

}
