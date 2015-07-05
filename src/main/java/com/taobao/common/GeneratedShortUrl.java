package com.taobao.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by star on 15/6/28.
 */
public class GeneratedShortUrl {
    private static final Logger LOG = LoggerFactory.getLogger(GeneratedShortUrl.class);

    private static final String SHORT_URL = "http://dwz.cn/create.php";

    private static final Map<String,String> EXPRESS = new ConcurrentHashMap<>();
    static {
        EXPRESS.put("中通快递","zhongtong");
        EXPRESS.put("圆通速递","yuantong") ;
        EXPRESS.put("申通快递","shentong") ;
        EXPRESS.put("京东快递","jd") ;
        EXPRESS.put("顺丰速运","shunfeng") ;
        EXPRESS.put("万象物流","wanxiangwuliu") ;
        EXPRESS.put("EMS","ems") ;
    }


    public static String getShortUrl(String company,String expressCode){
        String type = EXPRESS.get(company);
        if(StringUtils.isEmpty(type))
            return null;
        String url = "http://www.fuckbug.net:8080/amaze/express.html?type="+type+"&postid="+expressCode;
        return generated(url);
    }

    public static String generated(String url){
        Map<String,String> params = new HashMap<>();
        params.put("access_type","web");
        params.put("url",url);
        String data = post(params);
        JSONObject jsonObject = JSON.parseObject(data);
        if(jsonObject.get("err_msg").equals("")){
            return jsonObject.get("tinyurl").toString();
        }else {
            return null;
        }

    }

    public static void main(String[] args) {
        System.out.println(generated("http://www.taobao.com"));
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */
    public static String post(Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(SHORT_URL);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }


}
