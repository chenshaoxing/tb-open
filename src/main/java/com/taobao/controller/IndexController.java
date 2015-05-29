package com.taobao.controller;

import com.hr.system.manage.common.ResultJson;
import com.taobao.api.domain.User;
import com.taobao.entity.AutoRateSetting;
import com.taobao.service.AutoRateSettingService;
import com.taobao.service.RateContentService;
import com.taobao.service.SellerService;
import com.taobao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Resource
    private UserService userService;
    @Resource
    private RateContentService rateContentService;

    @Resource
    private AutoRateSettingService autoRateSettingService;

    @RequestMapping(value = "/index")
    public String index(HttpServletResponse response) throws Exception {

        User user = sellerService.getSellerInfo();
        com.taobao.entity.User uu = userService.findByName(user.getNick());
        if(uu == null){
            uu = new com.taobao.entity.User();
            uu.setNickname(user.getNick());
            uu = userService.add(uu);
            AutoRateSetting setting = new AutoRateSetting();
            setting.setAutoGrabRate(false);
            setting.setAutoRateStatus(false);
            setting.setMediumOrPoorRateAlarm(false);
            setting.setUser(uu);
            setting.setTriggerMode(AutoRateSetting.TriggerMode.BUYER_CONFIRM_RIGHT_AWAY_RATE);
            setting.setRateType(AutoRateSetting.RateType.good);
            autoRateSettingService.add(setting);
        }
        Cookie idCookie = new Cookie("id",String.valueOf(uu.getId()));
        Cookie nameCookie = new Cookie("name",uu.getNickname());
        response.addCookie(idCookie);
        response.addCookie(nameCookie);
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
