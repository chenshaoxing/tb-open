package com.taobao.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* Created by star on 4/1/15.
*/
//@Service
public class MailService {
    @Resource(name = "javaMailSender")
    private JavaMailSenderImpl javaMailSender;

    /**
     * 简单邮件发送
     *
     */
    public void simpleSend(String toEmail,String subject,String content) throws Exception{
        try{
            // 构建简单邮件对象，见名知意
            SimpleMailMessage smm = new SimpleMailMessage();
            // 设定邮件参数
            smm.setFrom(javaMailSender.getUsername());
            smm.setTo(toEmail);
            smm.setSubject(subject);
            smm.setText(content);
            // 发送邮件
            javaMailSender.send(smm);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
