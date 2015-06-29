package com.taobao.service;

import com.taobao.api.domain.Shipping;
import com.taobao.common.GeneratedShortUrl;
import com.taobao.common.JavaSmsApi;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by star on 15/6/28.
 */
@Service(value = "sendSmsService")
public class SendSmsService {
    private static final Logger LOG = LoggerFactory.getLogger(SendSmsService.class);

    @Resource
    private JavaSmsApi javaSmsApi;
    @Resource(name = "onlineEmailService")
    private OnlineEmailService emailService;

    public boolean sendSms(Shipping shipping,String email) throws Exception{
        try{
            String shortUrl = GeneratedShortUrl.getShortUrl(shipping.getCompanyName(),shipping.getOutSid());
            String text = "";
            if(StringUtils.isEmpty(shortUrl)) {
                text = "【程序员(猿)的杂货铺】亲，您购买的<"+shipping.getItemTitle()+">已发货。收货后五星好评有返现哟!";
            }else {
                text = "【程序员(猿)的杂货铺】亲，您购买的<"+shipping.getItemTitle()+">已发货，物流跟踪点击 "+shortUrl+"。收货后五星好评有返现哟";
            }
            boolean flag = javaSmsApi.sendSms(text,shipping.getReceiverMobile()) ;
            StringBuffer sb = new StringBuffer("");
            sb.append("商品:"+shipping.getItemTitle()+"<br/>");
            sb.append("收货人:"+shipping.getReceiverName()+"<br/>");
            sb.append("手机:"+shipping.getReceiverMobile()+"<br/>");
            sb.append("信息内容:"+text+"<br/>");
            String title = "";
            if(flag){
                title = "发货提醒成功";
            }else{
                title = "发货提醒失败";
            }
            emailService.sendEmail(email,title,sb.toString());
            return flag;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    };
}
