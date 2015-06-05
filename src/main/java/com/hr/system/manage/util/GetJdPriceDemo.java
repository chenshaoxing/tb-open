package com.hr.system.manage.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
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

        }
        response.close();
        httpClient.close();
    }


}
