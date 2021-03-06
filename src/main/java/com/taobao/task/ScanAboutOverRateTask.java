package com.taobao.task;

import com.taobao.common.ConfigurationManager;
import com.taobao.common.Constants;
import com.taobao.common.Utils;
import com.taobao.entity.AutoRateSetting;
import com.taobao.entity.NoRateOrders;
import com.taobao.entity.RateContent;
import com.taobao.entity.User;
import com.taobao.service.*;
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
            Map<String,Object> params = new HashMap() ;
            Date date = new Date();
            params.put("startTime", Utils.getToDayStartTimeStr(date,"yyyy-MM-dd"));
            params.put("endTime",Utils.getToDayEndTimeStr(date,"yyyy-MM-dd"));
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
                    LOG.error(e.getMessage());
                }
            }
            LOG.info("Scan About OverRate success");
        }catch (Exception e){
            emailService.sendEmail(Constants.ADMIN_EMAIL,"异常邮件","ERROR INFO:\n"+e.getMessage());
            LOG.error(e.getMessage());
        }
    }

    public int random(){
        Random random = new Random();
        int n3 = random.nextInt(3);
        n3 = Math.abs(random.nextInt() % 3);
        return n3;
    }
}
