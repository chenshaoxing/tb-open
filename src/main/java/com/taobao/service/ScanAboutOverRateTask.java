package com.taobao.service;

import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.NoRateOrders;
import com.taobao.entity.RateContent;
import com.taobao.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by star on 15/5/29.
 */
public class ScanAboutOverRateTask {
    private final static Logger LOG = LoggerFactory.getLogger(ScanAboutOverRateTask.class);

    @Resource
    private NoRateOrdersService noRateOrdersService;
    @Resource
    private RateService rateService;
    @Resource
    private AutoRateSettingService settingService;
    @Resource
    private RateContentService rateContentService;

    @Resource(name = "onlineEmailService")
    private OnlineEmailService emailService;

    public void scan(){
        try{
            LOG.info("Scan About OverRate start");
            emailService.sendEmail("45388540@qq.com","scan Email","在 xxx 执行了一次扫描yo");
            Map<String,Object> params = new HashMap() ;
            Date date = new Date();
            params.put("startTime",getToDayStartTime(date));
            params.put("endTime",getToDayEndTime(date));
            List<NoRateOrders> list = noRateOrdersService.getList(params);
            for(NoRateOrders noRateOrders:list){
                User user = noRateOrders.getUser();
                AutoRateSetting setting = settingService.findByUser(user);
                List<RateContent> content = rateContentService.findBySettingId(setting.getId());
                int index = random();
                RateContent rateContent = content.get(index);
                try {
                    boolean flag = rateService.add(setting.getId(),setting.getRateType().toString(),rateContent.getContent(),user.getSessionKey());
                    if(flag){
                        noRateOrdersService.delete(noRateOrders);
                    }
                } catch (Exception e) {
                    emailService.sendEmail("45388540@qq.com","scan ERROR",e.getMessage());
                    LOG.error(e.getMessage());
                }
            }
            LOG.info("Scan About OverRate end");
        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }

    public String getToDayStartTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String start = format.format(date);
        start = start+" 00:00:00";
        return  start;
    }

    public String getToDayEndTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String end = format.format(date);
        end = end+" 23:59:59";
       return end;
    }

    public int random(){
        Random random = new Random();
        int n3 = random.nextInt(3);

        n3 = Math.abs(random.nextInt() % 3);
        return n3;
    }
}
