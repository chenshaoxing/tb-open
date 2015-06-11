package com.taobao.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.common.Constants;
import com.taobao.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-11
 * Time: 18:13
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SessionKeyService {
    private static final Logger LOG = LoggerFactory.getLogger(SessionKeyService.class);

    private String appKey;
    private String secret;
    @Resource
    private UserService userService;


    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshUrl() {
        return refreshUrl;
    }

    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    private String refreshUrl;


    public User refreshSessionKeyAndSave(User user) throws Exception{
        try{
            String appkey = Constants.TB_SANDBOX_APP_KEY;
            String secret = Constants.TB_SANDBOX_APP_SECRET;
            String refreshToken = user.getRefreshToken();
            String sessionkey = user.getSessionKey();

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
            String signEncoder = URLEncoder.encode(sign, "utf-8");
            String appkeyEncoder = URLEncoder.encode(appkey,"utf-8");
            String refreshTokenEncoder = URLEncoder.encode(refreshToken,"utf-8");
            String sessionkeyEncoder = URLEncoder.encode(sessionkey,"utf-8");
//                String freshUrl = "http://container.api.tbsandbox.com/container/refresh?appkey="+appkeyEncoder+"&refresh_token="+refreshTokenEncoder+"&sessionkey="+sessionkeyEncoder+"&sign="+signEncoder;
            String freshUrl = refreshUrl+"container/refresh?appkey="+appkeyEncoder+"&refresh_token="+refreshTokenEncoder+"&sessionkey="+sessionkeyEncoder+"&sign="+signEncoder;
            System.out.println(freshUrl);
            String text = WebUtils.doGet(freshUrl, null);
            JSONObject jsonObject = JSON.parseObject(text);
            user.setRefreshToken(jsonObject.getString("refresh_token"));
            user.setSessionKey(jsonObject.getString("top_session"));
            user = userService.add(user);
            return user;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
