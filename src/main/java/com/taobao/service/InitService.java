package com.taobao.service;

import com.taobao.message.MessageClientDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * Created by star on 15/5/24.
 */
@Service
public class InitService implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger LOG = LoggerFactory.getLogger(InitService.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        MessageClientDemo messageClientDemo = (MessageClientDemo)contextRefreshedEvent.getApplicationContext().getBean("messageClientDemo");
        try {
            messageClientDemo.receive();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }
}
