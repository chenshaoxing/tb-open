package com.taobao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.domain.User;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.common.Constants;
import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.RateContent;
import com.taobao.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by star on 15/5/15.
 */
@Controller
public class AuthController {
    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private SellerService sellerService;
    @Resource
    private UserService userService;
    @Resource
    private AutoRateSettingService autoRateSettingService;
    @Resource
    private RateContentService rateContentService;
    @Resource
    private UserPermitService userPermitService;

    @RequestMapping(value = "/auth")
    public String auth(@RequestParam String code,@RequestParam String state,HttpServletResponse response) throws Exception{
        try{
            String objStr = getSession(code, state);
            JSONObject obj = JSON.parseObject(objStr);
            String sessionKey = obj.getString("access_token");
            String refreshToken = obj.getString("refresh_token");
            User user = sellerService.getSellerInfo(sessionKey);
            com.taobao.entity.User uu = userService.findByName(user.getNick());
            userPermitService.userPermit(sessionKey);
            if(uu == null){
                uu = new com.taobao.entity.User();
                uu.setRefreshToken(refreshToken);
                uu.setNickname(user.getNick());
                uu.setOverDate(new Date());
                uu.setSessionKey(sessionKey);
                uu = userService.add(uu);
                AutoRateSetting setting = new AutoRateSetting();
                setting.setAutoGrabRate(false);
                setting.setAutoRateStatus(false);
                setting.setMediumOrPoorRateAlarm(false);
                setting.setUser(uu);
                setting.setTriggerMode(AutoRateSetting.TriggerMode.BUYER_CONFIRM_RIGHT_AWAY_RATE);
                setting.setRateType(AutoRateSetting.RateType.good);
                setting =autoRateSettingService.add(setting);
                addRateContent(setting);
            }else{
                uu.setSessionKey(sessionKey);
                uu.setRefreshToken(refreshToken);
                userService.add(uu);
            }
            Cookie idCookie = new Cookie("id",String.valueOf(uu.getId()));
            Cookie nameCookie = new Cookie("name",URLEncoder.encode(uu.getNickname(), "utf-8"));
            response.addCookie(idCookie);
            response.addCookie(nameCookie);
            return "rate/rate-global-setting";
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }

    }

    private void addRateContent(AutoRateSetting setting){
       String [] contents = new String[]{"亲~~欢迎您下次光临小店~~~小店需要亲的大力支持~~·祝亲生活愉快！！",
               "非常感谢您的光临，希望您在本店选购的商品能给您带来快乐和满意！也许我们不是最好的，但我们一定会尽最大的努力做到更好，让每一位消费者都能真正的享受到网购的实惠和快乐！如果我们有什么地方做得不好，请您多多提提宝贵意见，因为在成长的路上我们需要您的支持和监督，这样我们就能做得更好！谢谢！",
               "每一个好评都是一份感动，每一个好评都是一种激励。最简单的好评，都给予我们的是动力和坚持，感谢亲的支持！欢迎下次光临！"};
       for(String content:contents){
           RateContent rateContent = new RateContent();
           rateContent.setAutoRateSetting(setting);
           rateContent.setContent(content);
           rateContentService.add(rateContent);
       }
    }

    @RequestMapping(value = "/main")
    public String index(Model model) {
        return "main/main";
    }

    public static void main(String[] args) throws Exception {
//        getSession();
        test();
    }

    public static String getSession(String code,String state) throws UnsupportedEncodingException {
        String url="https://oauth.taobao.com/token";
        Map<String,String> props=new HashMap<String,String>();
        props.put("grant_type","authorization_code");
/*测试时，需把test参数换成自己应用对应的值*/
        props.put("code",code);
        props.put("client_id","23175152");
        props.put("client_secret","fe3900d9e3c1e8c65b37da4d0237a953");
        props.put("redirect_uri","http://www.fuckbug.net:15569/auth");
        props.put("view","web");
        props.put("state",state);
        String s="";
        try{
            s= WebUtils.doPost(url, props, 30000, 30000);
            return s;
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }


     private  static void test() throws IOException {
         String url="https://oauth.taobao.com/token";
         Map<String, String> param = new HashMap<String, String>();
         param.put("grant_type", "refresh_token");
         param.put("refresh_token", Constants.TB_SANDBOX_SESSION_KEY);
         param.put("client_id","1023175152");
         param.put("client_secret",Constants.TB_SANDBOX_APP_SECRET);
         param.put("view","web");
         param.put("state","1212");
         String responseJson=WebUtils.doPost(url, param, 3000, 3000);
         System.out.println(responseJson);
     }


    public static void ping() throws Exception {
//        TaobaoClient client=new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23175152", "fe3900d9e3c1e8c65b37da4d0237a953");
//        TraderateAddRequest req=new TraderateAddRequest();
//        req.setTid(1048548482818420L);
////        req.setOid(1234L);
//        req.setResult("good");
//        req.setRole("seller");
//        req.setContent("很好的买家");
//        req.setAnony(true);
//        TraderateAddResponse response = client.execute(req , "6200107f34966f3ZZb7395547912167221b5990a9ebbc63178766584");
//        System.out.println(response.getTradeRate().getTid());
        getSession("SNDAFCeHHhIzcutaPMDE0wqt142823","1212");
    }
}
