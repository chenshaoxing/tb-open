package com.taobao.controller;

import com.taobao.common.ResultJson;
import com.taobao.common.Utils;
import com.taobao.dao.PageInfo;
import com.taobao.entity.BlackList;
import com.taobao.entity.User;
import com.taobao.service.BlackListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by star on 15/5/23.
 */
@Controller
public class BlackListController {

    private static final Logger LOG = LoggerFactory.getLogger(BlackListController.class);

    @Resource
    private BlackListService blackListService;

    @RequestMapping(value = "/black")
    public String index(){
        return "rate/black-list";
    }

    @RequestMapping(value = "/black/add")
    @ResponseBody
    public Map<String, Object> add(@RequestParam String buyerNick) throws Exception{
        try{
            Long count = blackListService.count(Utils.getUserId());
            if(count >= 500){
                return ResultJson.resultError();
            }
            BlackList blackList = new BlackList();
            blackList.setBuyerNickname(buyerNick);
            User user = new User();
            user.setId(Utils.getUserId());
            blackList.setUser(user);
            blackListService.add(blackList);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    @RequestMapping(value = "/black/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestParam Long id) throws Exception{
        try{
            blackListService.delete(id);
            return ResultJson.resultSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    @RequestMapping(value = "/black/getList")
    @ResponseBody
    public Map<String, Object> getList(@RequestParam int currentPage,
                                       @RequestParam int pageSize,
                                       @RequestParam(required = false) String buyerNick) throws Exception{
        try{
            PageInfo<BlackList> pageInfo = blackListService.getList(currentPage, pageSize, buyerNick, Utils.getUserId());
            return ResultJson.resultSuccess(pageInfo);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
