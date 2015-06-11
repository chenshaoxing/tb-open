package com.taobao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.domain.User;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.common.CipherTools;
import com.taobao.common.ConfigurationManager;
import com.taobao.common.Constants;
import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.RateContent;
import com.taobao.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
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
import java.net.URLEncoder;
import java.util.*;

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

    @RequestMapping(value = "/auth")
    public String auth(@RequestParam String code,@RequestParam String state,HttpServletResponse response) throws Exception{
        try{
            String objStr = getSession(code, state,response);
            JSONObject obj = JSON.parseObject(objStr);
            String sessionKey = obj.getString("access_token");
            if(StringUtils.isEmpty(sessionKey)){
                return "redirect:"+Constants.LOGOUT_URL;
            }
            String refreshToken = obj.getString("refresh_token");
            User user = sellerService.getSellerInfo(sessionKey);
            com.taobao.entity.User uu = userService.findByName(user.getNick());
            if(uu == null){
                Long expires = obj.getLong("expires_in");
                Long overDate = new Date().getTime()+expires*1000;
                uu = new com.taobao.entity.User();
                uu.setRefreshToken(refreshToken);
                uu.setNickname(user.getNick());
                uu.setOverDate(new Date(overDate));
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
            Cookie idCookie = new Cookie("id",cookieIdEncrypt(String.valueOf(uu.getId())));
            Cookie nameCookie = new Cookie("name",URLEncoder.encode(uu.getNickname(), "utf-8"));
            response.addCookie(idCookie);
            response.addCookie(nameCookie);
//            return "redirect:rate/rate-global-setting";
            return "redirect:rate/rate-global-setting";
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }

    }



//    @RequestMapping(value = "/authTest")
    public String authTest(HttpServletResponse response) throws Exception{
        try{

            String sessionKey = Constants.TB_SANDBOX_SESSION_KEY;
            if(StringUtils.isEmpty(sessionKey)){
                return "redirect:"+Constants.LOGOUT_URL;
            }
            User user = sellerService.getSellerInfo(sessionKey);
            com.taobao.entity.User uu = userService.findByName(user.getNick());
            if(uu == null){
                uu = new com.taobao.entity.User();
                uu.setRefreshToken(Constants.TB_SANDBOX_SESSION_KEY);
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
                uu.setRefreshToken(Constants.TB_SANDBOX_SESSION_KEY);
                userService.add(uu);
            }
            Cookie idCookie = new Cookie("id",cookieIdEncrypt(String.valueOf(uu.getId())));
            Cookie nameCookie = new Cookie("name",URLEncoder.encode(uu.getNickname(), "utf-8"));
            response.addCookie(idCookie);
            response.addCookie(nameCookie);
//            return "redirect:rate/rate-global-setting";
            return "redirect:rate/rate-global-setting";
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }

    }


    private String cookieIdEncrypt(String value) {
        CipherTools cipher = new CipherTools();
        return cipher.encrypt(value,Constants.COOKIE_CIPHER_KEY);
    }

    private void addRateContent(AutoRateSetting setting){
       String [] contents = new String[]{"亲~~欢迎您下次光临小店~~~小店需要亲的大力支持~~·祝亲生活愉快！！",
               "非常感谢您的光临，希望您在本店选购的商品能给您带来快乐和满意！也许我们不是最好的，但我们一定会尽最大的努力做到更好！",
               "每一个好评都是一份感动，每一个好评都是一种激励。最简单的好评，都给予我们的是动力和坚持，感谢亲的支持！欢迎下次光临！"};
       for(String content:contents){
           RateContent rateContent = new RateContent();
           rateContent.setAutoRateSetting(setting);
           rateContent.setContent(content);
           rateContentService.add(rateContent);
       }
    }


    public static String getSession(String code,String state,HttpServletResponse response) throws Exception {
        LOG.info("exec -------------------------------start");
        String url="https://oauth.taobao.com/token";
        Map<String,String> props=new HashMap<String,String>();
        props.put("grant_type","authorization_code");
        props.put("code",code);
        props.put("client_id",Constants.TB_ONLINE_APP_KEY);
        props.put("client_secret",Constants.TB_ONLINE_APP_SECRET);
        String redirectUrl = ConfigurationManager.create().get(Constants.BROWSE_URL);
        props.put("redirect_uri",redirectUrl);
        props.put("view","web");
        props.put("state",state);
        String s="";
        try{
            s= WebUtils.doPost(url, props, 30000, 30000);
            LOG.info("exec -------------------------------end\n"+s);
            return s;
        }catch(IOException e){
            JSONObject obj = JSON.parseObject(e.getMessage());
            LOG.error(e.getMessage());
            if(obj.getString("error").equals("invalid_client")){
                response.sendRedirect(Constants.LOGOUT_URL);
            }

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

    private static void url() throws Exception{
        String appkey = Constants.TB_SANDBOX_APP_KEY;
        String secret = Constants.TB_SANDBOX_APP_SECRET;
        String refreshToken = Constants.TB_SANDBOX_SESSION_KEY;
        String sessionkey = Constants.TB_SANDBOX_SESSION_KEY;

        Map<String,String> signParams = new TreeMap<String, String>();
        signParams.put("appkey", appkey);
        signParams.put("refresh_token", refreshToken);
        signParams.put("sessionkey", sessionkey);

        StringBuilder paramsString = new StringBuilder();
        Set<Map.Entry<String, String>> paramsEntry = signParams.entrySet();
        for(Map.Entry paramEntry : paramsEntry){
            paramsString.append(paramEntry.getKey()).append(paramEntry.getValue());
        }
        String sign = DigestUtils.md5Hex((paramsString.toString() + secret).getBytes("utf-8")).toUpperCase();
        String signEncoder = URLEncoder.encode(sign,"utf-8");
        String appkeyEncoder = URLEncoder.encode(appkey,"utf-8");
        String refreshTokenEncoder = URLEncoder.encode(refreshToken,"utf-8");
        String sessionkeyEncoder = URLEncoder.encode(sessionkey,"utf-8");
        String freshUrl = "http://container.api.tbsandbox.com/container/refresh?appkey="+appkeyEncoder+"&refresh_token="+refreshTokenEncoder+"&sessionkey="+sessionkeyEncoder+"&sign="+signEncoder;
        System.out.println(freshUrl);
        String text = WebUtils.doGet(freshUrl,null);
        JSONObject jsonObject = JSON.parseObject(text);
        System.out.println(text);
    }

    public static void main(String[] args) throws Exception{
        url();
    }


}
