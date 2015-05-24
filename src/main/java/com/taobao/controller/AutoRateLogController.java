package com.taobao.controller;

import com.hr.system.manage.common.ResultJson;
import com.taobao.dao.PageInfo;
import com.taobao.entity.AutoRateLog;
import com.taobao.entity.BlackList;
import com.taobao.service.AutoRateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 15/5/23.
 */
@Controller
public class AutoRateLogController {
    private static final Logger LOG = LoggerFactory.getLogger(AutoRateLogController.class);

    @Resource
    private AutoRateLogService autoRateLogService;

    @RequestMapping(value = "/auto-rate-log")
    public String index(){
        return "rate/auto-rate-log";
    }


    @RequestMapping(value = "/auto-rate-log/getList")
    @ResponseBody
    public Map<String, Object> getList(@RequestParam int currentPage,
                                       @RequestParam int pageSize,
                                       @RequestParam(required = false) String buyerNick,
                                       @RequestParam(required = false) Long tradeId,
                                       @RequestParam(required = false) String startTime,
                                       @RequestParam(required = false) String endTime
                                       ) throws Exception{
        try{
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("tradeId",tradeId);
            params.put("buyerNick",buyerNick);
            params.put("startTime",startTime);
            params.put("endTime",endTime);
            PageInfo<AutoRateLog> pageInfo = autoRateLogService.getList(currentPage, pageSize, params);
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
