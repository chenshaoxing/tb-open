package com.taobao.task;

import com.taobao.dao.PageInfo;
import com.taobao.entity.ShipperNotifyDelay;
import com.taobao.service.SendSmsService;
import com.taobao.service.ShipperNotifyDelayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by star on 15/7/12.
 */
public class DelaySendShipperSmsTask {
    private static final Logger LOG = LoggerFactory.getLogger(DelaySendShipperSmsTask.class);

    @Resource
    private ShipperNotifyDelayService shipperNotifyDelayService;
    @Resource
    private SendSmsService sendSmsService;

    public void run(){
        PageInfo<ShipperNotifyDelay> pageInfo = shipperNotifyDelayService.getList(1, 10);
        Long totalPage =  pageInfo.getPageTotalNum();
        for(int i = 1;i<totalPage+1;i++){
            pageInfo = shipperNotifyDelayService.getList(i, 100);
            totalPage = pageInfo.getPageTotalNum();
            sendSmsService.sendSms(pageInfo.getList());
        }

        List<ShipperNotifyDelay> success = shipperNotifyDelayService.successList();
        for(ShipperNotifyDelay sp:success){
            shipperNotifyDelayService.delete(sp);
        }

    }


    private PageInfo<ShipperNotifyDelay> getBatchRateOrders(int currentPage,
                                               int pageSize) throws Exception{
        try{
            PageInfo<ShipperNotifyDelay> pageInfo = shipperNotifyDelayService.getList(currentPage, pageSize);
            return pageInfo;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
