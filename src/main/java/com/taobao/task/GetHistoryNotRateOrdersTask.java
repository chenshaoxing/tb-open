package com.taobao.task;

import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.common.ConfigurationManager;
import com.taobao.common.Constants;
import com.taobao.dao.PageInfo;
import com.taobao.entity.NoRateOrders;
import com.taobao.entity.User;
import com.taobao.service.NoRateOrdersService;
import com.taobao.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

/**
 * Created by star on 15/5/31.
 */
public class GetHistoryNotRateOrdersTask implements Runnable {
    private final static Logger LOG = LoggerFactory.getLogger(GetHistoryNotRateOrdersTask.class);

    private User user;
    private TradeService tradeService;
    private NoRateOrdersService noRateOrdersService;


    public GetHistoryNotRateOrdersTask(User  user,TradeService tradeService,NoRateOrdersService noRateOrdersService) {
        this.user = user;
        this.tradeService = tradeService;
        this.noRateOrdersService = noRateOrdersService;
    }

    @Override
    public void run() {
        try{
            PageInfo<Trade> pageInfo = this.getBatchRateOrders(1l,10l);
            Long totalPage =  pageInfo.getPageTotalNum();
            for(long i = 1l;i<totalPage+1l;i++){
                pageInfo = this.getBatchRateOrders(i,100l);
                totalPage = pageInfo.getPageTotalNum();
                insertNoRateOrder(pageInfo.getList());
            }
            LOG.info("GetHistoryNotRateOrdersTask success");
        }catch (Exception e){
            LOG.error(e.getMessage());
        }

    }


    private void insertNoRateOrder(List<Trade> result){
        for(Trade trade:result){
            NoRateOrders noRateOrders = null;

            noRateOrders = noRateOrdersService.findByTradeId(trade.getTid());
            if(noRateOrders != null){
                continue;
            }
            noRateOrders = new NoRateOrders();
            noRateOrders.setPayment(Float.valueOf(trade.getPayment()));
            noRateOrders.setBuyer(trade.getBuyerNick());
            noRateOrders.setUser(user);
            noRateOrders.setRate(false);
            noRateOrders.setTid(trade.getTid());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(trade.getEndTime());
            int beforeDay = ConfigurationManager.create().getInt(Constants.BEFORE_OVERTIME_EXEC_RATE_DATE,14);
            calendar.add(Calendar.DATE, beforeDay);

            noRateOrders.setOverTime(calendar.getTime());
            noRateOrdersService.add(noRateOrders);
        }
    }

    private PageInfo<Trade> getBatchRateOrders(Long currentPage,
                                                  Long pageSize) throws Exception{
        try{
            TradesSoldGetRequest request = new TradesSoldGetRequest();
            request.setPageNo(currentPage);
            request.setPageSize(pageSize);
            request.setRateStatus("RATE_UNSELLER");
//            TradesSoldGetResponse response = tradeService.getTradeSold(request,user.getSessionKey());
            TradesSoldGetResponse response = tradeService.getTradeSold(request,user.getSessionKey());
            PageInfo pageInfo = new PageInfo(pageSize,response.getTotalResults());
            pageInfo.setList(response.getTrades());
            return pageInfo;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
