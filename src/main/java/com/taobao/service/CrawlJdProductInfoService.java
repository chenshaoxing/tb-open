package com.taobao.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.common.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-04
 * Time: 15:26
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service(value = "crawlJdProductInfoService")
public class CrawlJdProductInfoService {
    private static final Logger LOG = LoggerFactory.getLogger(CrawlJdProductInfoService.class);

    @Resource(name = "onlineEmailService")
    private OnlineEmailService emailService;

    public Map<String,Object> crawl(String skuId) throws Exception{
        int count = 0;
        return call(skuId,count);
    }

    /**
     * key:sort(排序),price(最新价格),name(商品名称),crawlUrl(商品url),pic(图片地址),skuid(id),discount(优惠金额)
     * @param skuId
     * @return
     * @throws Exception
     */
    private  Map<String,Object> call(String skuId,int count){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String urls = Constants.CRAWL_JD_PRODUCT_INFO_URL_PREFIX+"skuId="+skuId+"&_="+new Date().getTime();
        HttpGet httpGet = new HttpGet(urls);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            LOG.info(response.getStatusLine() + "");
            Map<String,Object> result = null;
            if(entity != null){
                String data = EntityUtils.toString(entity);
                data = data.substring(data.indexOf("(")+1,data.lastIndexOf(")"));
                JSONObject obj = JSON.parseObject(data);
                result = (Map<String,Object>)obj.get("master");

                //获取最新价格
                HttpGet httpGetPrice = new HttpGet(Constants.CRAWL_JD_PRODUCT_PRICE_URL+"skuid=J_"+skuId);
                response = httpClient.execute(httpGetPrice);
                HttpEntity priceEntity = response.getEntity();
                if(entity != null){
                    String dataPrice = EntityUtils.toString(priceEntity);
                    LOG.info("Response content: " +dataPrice );
                    dataPrice = dataPrice.substring(dataPrice.indexOf("(")+1,dataPrice.lastIndexOf(")"));
                    dataPrice = dataPrice.substring(dataPrice.indexOf("[")+1,dataPrice.indexOf("]"));
                    JSONObject priceOjb = JSON.parseObject(dataPrice);
                    result.put("price",priceOjb.get("p"));
                }
                result.put("crawlUrl",urls);
            }
            return result;
        } catch (Exception e) {
            if(count == 5){
                LOG.error(skuId + "error");
                LOG.error(urls);
                LOG.error(e.getMessage());
                emailService.sendEmail(Constants.ADMIN_EMAIL, "商品 " + skuId + " 重试次数达到5次价格抓取失败", urls);
                return null;
            }else{
                count++;
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e1) {
                    LOG.error(e1.getMessage(),e1);
                }
                LOG.info("商品 " + skuId + " retry count "+count);
                return call(skuId,count);
            }
        } finally {
            IOUtils.closeQuietly(response);
            IOUtils.closeQuietly(httpClient);
        }
    }

    public static void main(String[] args) throws Exception{
        CrawlJdProductInfoService service = new CrawlJdProductInfoService();
        String skUid = "596266";
        Map<String,Object> obj = service.crawl(skUid);
        float f = -2f;
        if(f<0){

        System.out.println(obj);
        }
    }

}
