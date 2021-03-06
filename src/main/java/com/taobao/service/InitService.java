package com.taobao.service;

import com.taobao.message.MessageClient;
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
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            MessageClient messageClient = new MessageClient(contextRefreshedEvent.getApplicationContext());
            try {
                messageClient.receive();
                LOG.info("message center init success");
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        }

    }
}
