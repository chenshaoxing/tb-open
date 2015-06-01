package com.taobao.service;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-01-04
 * Time: 10:53
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class OnlineEmailService {
    private String mailServerHost;
    private Integer mailServerPort;
    private String userName;
    private String password;
    private static final String DEFAULT_MAIL_VM = "mail.vm";

    private static final Logger LOG = LoggerFactory.getLogger(OnlineEmailService.class);

    @Resource
    private VelocityEngine velocityEngine;

    private JavaMailSenderImpl javaMailSender;

    public void  init(){
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailServerHost);
        javaMailSender.setPort(mailServerPort);
        javaMailSender.setUsername(userName);
        javaMailSender.setPassword(password);
        javaMailSender.setDefaultEncoding("utf-8");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth","true");
        prop.put("mail.smtp,timeout","25000");
        javaMailSender.setJavaMailProperties(prop);
    }

    public void sendEmail(String emailAddress, String subject, String content) throws Exception {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        try {
            Map<String, Object> model = new HashMap();
            model.put("user", "Dear");
            model.put("content", content);
            String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, DEFAULT_MAIL_VM, "utf-8", model);
            messageHelper.setFrom(userName);
            messageHelper.setTo(emailAddress);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailText,true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public void sendEmail(List<String> emailAddress, String subject, String content) throws Exception {
        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        try {
            Map<String, Object> model = new HashMap();
            model.put("user", "Dear");
            model.put("content", content);
            String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, DEFAULT_MAIL_VM, "utf-8", model);
            messageHelper.setFrom(userName);
            String []  address  = emailAddress.toArray(new String[emailAddress.size()]);
            messageHelper.setTo(address);
            messageHelper.setSubject(subject);
            messageHelper.setText(emailText, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }



    public void sendEmail(List<String> emailAddress, String subject, String vmPath, Map<String, Object> model) throws Exception {
        String emailText = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, vmPath, "utf-8", model);
        this.sendEmail(emailAddress,subject,emailText);
    }

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }

    public void setMailServerPort(Integer mailServerPort) {
        this.mailServerPort = mailServerPort;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
