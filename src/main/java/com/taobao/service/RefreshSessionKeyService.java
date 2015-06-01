package com.taobao.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.internal.util.WebUtils;
import com.taobao.common.Constants;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by star on 15/5/31.
 */
@Service
public class RefreshSessionKeyService {

    private static final Logger LOG = LoggerFactory.getLogger(RefreshSessionKeyService.class);

    public JSONObject refreshSessionKey() throws Exception {
        String appkey = Constants.TB_SANDBOX_APP_KEY;
        String secret = Constants.TB_SANDBOX_APP_SECRET;
        String refreshToken = Constants.TB_SANDBOX_SESSION_KEY;
        String sessionkey = Constants.TB_SANDBOX_SESSION_KEY;
        try {
            Map<String, String> signParams = new TreeMap<String, String>();
            signParams.put("appkey", appkey);
            signParams.put("refresh_token", refreshToken);
            signParams.put("sessionkey", sessionkey);
            StringBuilder paramsString = new StringBuilder();
            Set<Map.Entry<String, String>> paramsEntry = signParams.entrySet();
            for (Map.Entry paramEntry : paramsEntry) {
                paramsString.append(paramEntry.getKey()).append(paramEntry.getValue());
            }
            String sign = DigestUtils.md5Hex((paramsString.toString() + secret).getBytes("utf-8")).toUpperCase();
            String signEncoder = URLEncoder.encode(sign, "utf-8");
            String appkeyEncoder = URLEncoder.encode(appkey, "utf-8");
            String refreshTokenEncoder = URLEncoder.encode(refreshToken, "utf-8");
            String sessionkeyEncoder = URLEncoder.encode(sessionkey, "utf-8");

            String freshUrl = "http://container.api.tbsandbox.com/container/refresh?appkey=" + appkeyEncoder
                    + "&refresh_token=" + refreshTokenEncoder + "&sessionkey=" + sessionkeyEncoder + "&sign="
                    + signEncoder;
            System.out.println(freshUrl);
            String response = WebUtils.doPost(freshUrl, null, 30 * 1000 * 60, 30 * 1000 * 60);
            System.out.println(response);
            return JSON.parseObject(response);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        RefreshSessionKeyService sessionKeyService = new RefreshSessionKeyService();
        sessionKeyService.refreshSessionKey();
    }
}
