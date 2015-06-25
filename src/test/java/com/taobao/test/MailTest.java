package com.taobao.test;

import com.taobao.service.OnlineEmailService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by star on 15/6/1.
 */
public class MailTest {
    @Test
    public void send() throws Exception {
//        ApplicationContext act = new ClassPathXmlApplicationContext("applicationContext.xml");
//        OnlineEmailService service = (OnlineEmailService)act.getBean("onlineEmailService");
//        service.sendEmail("45388540@qq.com","sss","ffdfdfdfdf");
        String s = "xsc";
        switch (s){
            case "csx":
                System.out.println(1);
                break;
            case "xsc":
                System.out.println(2);
                break;
        }

    }
}
