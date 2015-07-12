package com.taobao.service;

import com.taobao.api.domain.Shipping;
import com.taobao.common.GeneratedShortUrl;
import com.taobao.common.JavaSmsApi;
import com.taobao.common.Utils;
import com.taobao.entity.ShipperNotifyDelay;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private ShipperNotifyDelayService shipperNotifyDelayService;

    public boolean sendSms(Shipping shipping,String email) throws Exception{
        try{
            String shortUrl = GeneratedShortUrl.getShortUrl(shipping.getCompanyName(),shipping.getOutSid());
            String text = "";
            if(StringUtils.isEmpty(shortUrl)) {
                text = "【程序员(猿)的杂货铺】亲，您购买的<"+shipping.getItemTitle()+">已发货。收货后五星好评有返现哟!";
            }else {
                text = "【程序员(猿)的杂货铺】亲，您购买的<"+shipping.getItemTitle()+">已发货，物流跟踪点击 "+shortUrl+"。收货后五星好评有返现哟";
            }

            StringBuffer sb = new StringBuffer("");
            sb.append("商品:"+shipping.getItemTitle()+"<br/>");
            sb.append("收货人:"+shipping.getReceiverName()+"<br/>");
            sb.append("手机:"+shipping.getReceiverMobile()+"<br/>");
            sb.append("信息内容:"+text+"<br/>");
            String title = "";
            if(Utils.currentDateIsNight()){
                ShipperNotifyDelay shipperNotifyDelay = new ShipperNotifyDelay();
                shipperNotifyDelay.setMobile(shipping.getReceiverMobile());
                shipperNotifyDelay.setSmsContent(text);
                shipperNotifyDelay.setSellerEmail(email);
                shipperNotifyDelay.setSellerEmailContent(sb.toString());
                shipperNotifyDelayService.save(shipperNotifyDelay);
                LOG.info("send sms:"+shipping.getReceiverMobile()+"  延时发送");
                return true;
            }else{
                boolean flag = javaSmsApi.sendSms(text,shipping.getReceiverMobile());
                if(flag){
                    title = "发货提醒成功";
                }else{
                    title = "发货提醒失败";
                }
                emailService.sendEmail(email,title,sb.toString());
                return flag;
            }
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    };

    public void sendSms(List<ShipperNotifyDelay> spList){
        for(ShipperNotifyDelay sp:spList){
            try{
                boolean flag = javaSmsApi.sendSms(sp.getSmsContent(),sp.getMobile());
                String title = "";
                if(flag){
                    title = "发货提醒成功";
                    sp.setSuccess(true);
                    shipperNotifyDelayService.save(sp);
                }else{
                    title = "发货提醒失败";
                }
                emailService.sendEmail(sp.getSellerEmail(),title,sp.getSellerEmailContent());
            }catch (Exception e1){
                LOG.error(e1.getMessage());
                continue;
            }
        }
    }
}
