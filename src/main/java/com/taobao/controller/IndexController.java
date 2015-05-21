package com.taobao.controller;

import com.hr.system.manage.common.ResultJson;
import com.taobao.api.domain.User;
import com.taobao.service.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-21
 * Time: 10:20
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class IndexController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private SellerService sellerService;

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/index/seller-info")
    @ResponseBody
    public Map<String, Object> getSellerInfo() throws Exception{
        try{
            User user = sellerService.getSellerInfo();
            return ResultJson.resultSuccess(user);
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
