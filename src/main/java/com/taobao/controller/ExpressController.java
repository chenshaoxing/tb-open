package com.taobao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.common.Constants;
import com.taobao.common.ResultJson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

/**
 * Created by star on 15/6/18.
 */
@Controller
public class ExpressController {
    private static final Logger LOG = LoggerFactory.getLogger(ExpressController.class);
    private static final String EXPRESS_URL = "http://www.kuaidi100.com/query?";

    @RequestMapping(value = "/express")
    @ResponseBody
    public Map<String,Object> query(@RequestParam String type,@RequestParam String postId) throws Exception{
        try{
            return ResultJson.resultSuccess(crawl(type,postId));
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }

    }



    public static   JSONObject crawl(String type,String postId) throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String urls = EXPRESS_URL+"type="+type+"&postid="+postId+"&id=1&valicode=&temp=0";
        HttpGet httpGet = new HttpGet(urls);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println(response.getStatusLine());
            if(entity != null){
                String data = EntityUtils.toString(entity);
                JSONObject obj = JSON.parseObject(data);
                return obj;
            }
            return null;
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw e;
        } finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    }


    public static void main(String[] args) throws Exception{
        crawl("jd","9402232835");
    }
}
