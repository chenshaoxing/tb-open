package com.hr.system.manage.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by star on 15/4/30.
 */
public class GetJdPriceDemo {
    public static void main(String[] args) throws Exception {
//        jd();
        get();


    }

    private static void jd() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://p.3.cn/prices/get?skuid=J_627718&type=1&area=22_1930_50947&callback=cnp");
        System.out.println("executing request "+httpGet.getURI());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        System.out.println(response.getStatusLine());
        if(entity != null){
            System.out.println("Response content length: " + entity.getContentLength());
            // 打印响应内容
            String data = EntityUtils.toString(entity);
            System.out.println("Response content: " +data );
            data = data.substring(data.indexOf("(")+1,data.lastIndexOf(")"));

        }
        response.close();
        httpClient.close();
    }

    private static void get() throws Exception{
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://passport.jd.com/uc/login?ltype=logout");
//        HttpGet httpGet = new HttpGet("http://active.coupon.jd.com/ilink/couponActiveFront/front_index.action?key=13c5c272bbeb40bfac4934830f8183a8&roleId=1055399&to=sale.jd.com/act/flgmvjxtai.html");
//        httpGet.setHeader();
        System.out.println("executing request "+httpGet.getURI());
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        System.out.println(response.getStatusLine());
        if(entity != null){
            System.out.println("Response content length: " + entity.getContentLength());
            // 打印响应内容
            String data = EntityUtils.toString(entity);
            Document document = Jsoup.parse(data);
            Element uuid = document.getElementById("uuid");
            System.out.println(uuid.val());
            Element machineDisk = document.getElementById("machineDisk");
            Element key = machineDisk.nextElementSibling();

            System.out.println(key.attr("name"));
            System.out.println(key.val());

            HttpPost httppost = new HttpPost("https://passport.jd.com/uc/loginService?uuid="+uuid.val()+"&ltype=logout&r="+ Math.random()+"&version=2015");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("loginname", "lucky_xingxing"));
            params.add(new BasicNameValuePair("nloginpwd","xx1991"));
            params.add(new BasicNameValuePair("loginpwd","xx1991"));
            params.add(new BasicNameValuePair("uuid",uuid.val()));
            params.add(new BasicNameValuePair("machineNet",""));
            params.add(new BasicNameValuePair("machineCpu",""));
            params.add(new BasicNameValuePair("machineDisk",""));
            params.add(new BasicNameValuePair("authcode",""));
            params.add(new BasicNameValuePair("chkRememberMe","on"));
            params.add(new BasicNameValuePair(key.attr("name"),key.val()));
            httppost.setEntity(new UrlEncodedFormEntity(params));
            HttpResponse re = httpClient.execute(httppost);
            HttpEntity entity1 = re.getEntity();
            System.out.println(EntityUtils.toString(entity1));

            String cardUrl = "http://active.coupon.jd.com/ilink/couponActiveFront/front_index.action?key=13c5c272bbeb40bfac4934830f8183a8&roleId=1055399&to=sale.jd.com/act/flgmvjxtai.html";
            HttpGet cardHttpGet = new HttpGet(cardUrl);
            CloseableHttpResponse responseCard = httpClient.execute(cardHttpGet);
            String ll = EntityUtils.toString(responseCard.getEntity());
            System.out.println(ll);
            Document documentResult = Jsoup.parse(ll);
            Elements elements = documentResult.getElementsByClass("ctxt02");
            for(Element e:elements){
                System.out.println(e.text());
            }
//            System.out.println(responseCard.getStatusLine());
        }
        response.close();
        httpClient.close();
    }


}
