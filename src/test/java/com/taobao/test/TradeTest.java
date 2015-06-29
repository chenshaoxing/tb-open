package com.taobao.test;

import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import com.taobao.service.SendSmsService;
import com.taobao.service.TradeService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by star on 15/6/1.
 */
public class TradeTest {
    @Test
    public void send() throws Exception {
        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
        TradeService service = (TradeService)act.getBean("tradeService");
//        Trade trade = service.getTradeBuyerContactInfo(1102632674594145L, "6200022bd7037de36c22c8dabe94d4ZZaa1949272619d2b178766584");
//        Shipping shipping = service.getExpressInfo(1102632674594145L, "6200022bd7037de36c22c8dabe94d4ZZaa1949272619d2b178766584");
        SendSmsService sendSmsService = (SendSmsService)act.getBean("sendSmsService");
        Shipping shipping = new Shipping();
        shipping.setReceiverMobile("18680188225");
        shipping.setOutSid("100403151835");
        shipping.setCompanyName("圆通快递");
        shipping.setItemTitle("促销 澳洲原装进口 德运脱脂纯牛奶1L*10 瓶 原装进口 同城即日达");
        sendSmsService.sendSms(shipping,"45388540@qq.com");
        System.out.println(1);

    }
}
