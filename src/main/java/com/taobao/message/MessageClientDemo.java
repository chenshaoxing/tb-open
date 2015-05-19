package com.taobao.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.common.Constants;
import com.taobao.service.RateService;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * Created by star on 15/5/17.
 */
public class MessageClientDemo {
    private RateService rateService = new RateService();

    public static void main(String[] args) throws Exception {
        MessageClientDemo m = new MessageClientDemo();
        m.receive();
    }

    public void  receive() throws Exception{
        TmcClient client = new TmcClient("ws://mc.api.tbsandbox.com/","1023175152","sandbox9e3c1e8c65b37da4d0237a953","default");
        client.setMessageHandler(new MessageHandler() {
            @Override
            public void onMessage(Message message, MessageStatus messageStatus) throws Exception {

                System.out.println(message.getContent());

                Map<String,Object> result = message.getRaw();
                String topic = result.get("topic").toString();
                JSONObject object = JSON.parseObject(message.getContent());

                if(topic.equals(Constants.TAOBAO_TRADE_TRADESUCCESS)){
                    Long tid = Long.valueOf(object.get("tid").toString());
                    rateService.add(tid, RateService.RateEnum.GOOD,"很好的买家，给你32个赞");
                }else if(topic.equals(Constants.TAOBAO_TRADE_TRADERATED)){
                    String rater = object.get("rater").toString();
                    if(rater.equals("buyer")){
                        Long tid = Long.valueOf(object.get("tid").toString());
                        Long oid = Long.valueOf(object.get("oid").toString());
                        rateService.add(tid,oid, RateService.RateEnum.GOOD,"很好的买家，给你33个赞噢");
                    }

                }
//                for(String key:message.getRaw().keySet()){
//                    System.out.println("key:"+key+" value:"+message.getRaw().get(key));
//                }
            }
        });
        client.connect();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }
}
