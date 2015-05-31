package com.taobao.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.taobao.api.domain.TradeRate;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.common.Constants;
import com.taobao.entity.*;
import com.taobao.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * Created by star on 15/5/17.
 */
//@Component(value = "messageClientDemo")
public class MessageClientDemo {
    private static final Logger LOG = LoggerFactory.getLogger(MessageClientDemo.class);

    private RateService rateService  = null;
    private AutoRateSettingService autoRateSettingService = null;
    private AutoRateLogService autoRateLogService= null;
    private BlackListService blackListService= null;
    private RateContentService rateContentService= null;
    private UserService userService= null;
    private NoRateOrdersService noRateOrdersService= null;
    private MailService mailService= null;
    public MessageClientDemo(ApplicationContext act) {
        rateService = (RateService)act.getBean("rateService");
        autoRateSettingService = (AutoRateSettingService)act.getBean("autoRateSettingServiceImpl");
        autoRateLogService = (AutoRateLogService)act.getBean("autoRateLogServiceImpl");
        blackListService = (BlackListService) act.getBean("blackListServiceImpl");
        rateContentService = (RateContentService)act.getBean("rateContentServiceImpl");
        userService = (UserService)act.getBean("userServiceImpl");
        noRateOrdersService = (NoRateOrdersService)act.getBean("noRateOrdersServiceImpl");
        mailService = (MailService)act.getBean("mailService");
    }




//    public static void main(String[] args) throws Exception {
//        MessageClientDemo m = new MessageClientDemo();
//        m.receive();
//    }

    public void  receive() throws Exception{
        TmcClient client = new TmcClient(Constants.TB_ONLINE_MESSAGE_URL,Constants.TB_ONLINE_APP_KEY,Constants.TB_ONLINE_APP_SECRET,"default");
        client.setMessageHandler(new MessageHandler() {
            @Override
            public void onMessage(Message message, MessageStatus messageStatus) throws Exception {
                Map<String,Object> result = message.getRaw();
                String topic = result.get("topic").toString();
                JSONObject object = JSON.parseObject(message.getContent());

                String buyerNick = object.getString("buyer_nick");
                BlackList blackList = blackListService.findByName(buyerNick);
                if(blackList != null){
                    LOG.info("find blacklist buyer nick is:"+buyerNick);
                    return;
                }
                String sellerNick = object.getString("seller_nick");

                Long tid = Long.valueOf(object.get("tid").toString());
                Long oid = Long.valueOf(object.get("oid").toString());
                if(StringUtils.isNotEmpty(sellerNick)){
                    User user = userService.findByName(object.getString("seller_nick"));
                    if(user == null)
                        return;
                    AutoRateSetting autoRateSetting = autoRateSettingService.findByUser(user);
                    List<RateContent> content = rateContentService.findBySettingId(autoRateSetting.getId());
                    int index = random();
                    RateContent rateContent = content.get(index);
                    object.put("rateEnum",autoRateSetting.getRateType());
                    object.put("rateContent",rateContent);
                    if(autoRateSetting.isAutoRateStatus()){
                        if(topic.equals(Constants.TAOBAO_TRADE_TRADESUCCESS)){
                            NoRateOrders noRate = addNoRateOrders(object);
                            if(autoRateSetting.getTriggerMode().name().equals(AutoRateSetting.TriggerMode.BUYER_CONFIRM_RIGHT_AWAY_RATE.name())){
                                boolean isRate = rateService.add(tid,autoRateSetting.getRateType().toString(),rateContent.getContent(),user.getSessionKey());
                                if(isRate){
                                    noRate.setRate(true);
                                    noRateOrdersService.add(noRate);
//                                    badOrNeutralSendEmail(tid,user);
                                    addAutoRateLog(object);
                                }
                            }
                        }else if(topic.equals(Constants.TAOBAO_TRADE_TRADERATED)){
                            String rater = object.getString("rater");
                            if(rater.equals("buyer")){
                                if(autoRateSetting.getTriggerMode().name().equals(AutoRateSetting.TriggerMode.BUYER_RATE_RIGHT_AWAY_RATE.name())){
                                    boolean isRate = rateService.add(tid, oid, autoRateSetting.getRateType().toString(), rateContent.getContent(),user.getSessionKey());
                                    if(isRate){
                                        addAutoRateLog(object);
//                                        badOrNeutralSendEmail(tid,user);
                                        NoRateOrders noRate = noRateOrdersService.findByTradeId(tid);
                                        if(noRate != null){
                                            noRate.setRate(true);
                                            noRateOrdersService.add(noRate);
                                        }
                                    }
                                }
//                            else  if(autoRateSetting.getTriggerMode() == AutoRateSetting.TriggerMode.BUYER_RATE_NOT_RATE){

//                            }

                            }

                        }
                    }
                }
//                for(String key:message.getRaw().keySet()){
//                    System.out.println("key:"+key+" value:"+message.getRaw().get(key));
//                }
            }
        });
        client.connect();
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
    }

    private NoRateOrders addNoRateOrders(JSONObject object) {
        NoRateOrders noRate = new NoRateOrders();
        noRate.setTid(object.getLong("tid"));
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, 14);
        noRate.setRate(false);
        noRate.setOverTime(calendar.getTime());
        noRate.setBuyer(object.getString("buyer_nick"));
        noRate.setPayment(object.getFloat("payment"));
        noRate = noRateOrdersService.add(noRate);
        return noRate;
    }


    public void badOrNeutralSendEmail(Long tradeId,User user){
        TraderatesGetRequest request = new TraderatesGetRequest();
        if(tradeId != null){
            request.setTid(tradeId);
        }
        request.setPageNo(1L);
        request.setRateType("get");
        request.setRole("buyer");
        try {
            TraderatesGetResponse response = rateService.searchRate(request);
            List<TradeRate> list = response.getTradeRates();
            for(TradeRate tradeRate:list){
                if(tradeRate.getResult().equals("bad")){
                    mailService.simpleSend(user.getEmail(),"差评通知","亲爱的，您收到一个差评订单，买家id是，请尽快和买家联系");
                }else if(tradeRate.getResult().equals("neutral")){
                    mailService.simpleSend(user.getEmail(),"中评通知","亲爱的，您收到一个中评订单，买家id是，请尽快和买家联系");
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }


    public void addAutoRateLog(JSONObject object){
        AutoRateLog log = new AutoRateLog();
        log.setTid(object.getLong("tid"));
        log.setBuyerNickname(object.getString("buyer_nick"));
        log.setRateTime(new Date());
        log.setRealPrice(object.getFloat("payment"));
        log.setRateContent((RateContent) object.get("rateContent"));
        log.setRateStatus(RateStatus.AUTO_RATE);
        log.setRateEnum((AutoRateSetting.RateType)object.get("rateEnum"));
        autoRateLogService.add(log);
    }


    public static void main(String[] args) {

    }

    public int random(){
        Random random = new Random();
        int n3 = random.nextInt(3);

        n3 = Math.abs(random.nextInt() % 3);
        return n3;
    }
}
