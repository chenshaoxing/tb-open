package com.taobao.test;

import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import com.taobao.entity.TaoBaoProduct;
import com.taobao.entity.TbRelationJd;
import com.taobao.entity.User;
import com.taobao.service.*;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;

/**
 * Created by star on 15/6/1.
 */
public class TradeTest {
    @Test
    public void send() throws Exception {
        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
        TradeService service = (TradeService)act.getBean("tradeService");
        BuyerBuyInfoService buyerBuyInfoService = (BuyerBuyInfoService)act.getBean("buyerBuyInfoService");
        SendSmsService sendSmsService = (SendSmsService)act.getBean("sendSmsService");
        UserService userService = (UserService)act.getBean("userServiceImpl");
//        TbRelationJdService tbRelationJdService = (TbRelationJdService)act.getBean("tbRelationJdServiceImpl");
//        TaoBaoProductService taoBaoProductService = (TaoBaoProductService)act.getBean("taoBaoProductServiceImpl");
//        TbRelationJd tbRelationJd = tbRelationJdService.findByTbIdAndJdId(3L, 1L);
//        TaoBaoProduct tb = tbRelationJd.getProduct();
//        tb.setPrice(45f);
//        taoBaoProductService.update(tb);
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        countDownLatch.await();
        User user = userService.findById(1L);

        Shipping sp = service.getExpressInfo(1096331902306490L, user.getSessionKey());
//        Trade trade = service.getTradeInfo(sp.getTid(),user.getSessionKey());

//        User user = new User();
//        user.setEmail("45388540@qq.com");
//        user = userService.findById(1L);

//        sendSmsService.sendSms(sp,user.getEmail());
        buyerBuyInfoService.add(sp,user);


//

    }
}
