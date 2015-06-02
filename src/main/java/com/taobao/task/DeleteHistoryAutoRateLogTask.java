package com.taobao.task;


import com.taobao.dao.PageInfo;
import com.taobao.entity.AutoRateLog;
import com.taobao.entity.AutoRateSetting;
import com.taobao.service.AutoRateLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by star on 15/5/30.
 */
public class DeleteHistoryAutoRateLogTask {
    private static final Logger LOG = LoggerFactory.getLogger(DeleteHistoryAutoRateLogTask.class);

    @Resource
    private AutoRateLogService autoRateLogService;

    public void delete(){
        try{
            LOG.info("scan delete autoRateLog start");
            Date date = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -5);
//        calendar.add(Calendar.DATE, -30);
            Map<String,Object> params = new HashMap();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            params.put("endTime",sf.format(calendar.getTime()));
            PageInfo pageInfo = autoRateLogService.getList(1, Integer.MAX_VALUE, params);
            List<AutoRateLog> logs = pageInfo.getList();
            for(AutoRateLog log : logs){
                autoRateLogService.remove(log);
            }
            LOG.info("scan delete autoRateLog end. delete record "+logs.size());
        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }
}
