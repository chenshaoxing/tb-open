package com.taobao.service;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeSnapshotGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeSnapshotGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-21
 * Time: 10:07
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TradeService {
    private static final Logger LOG = LoggerFactory.getLogger(TradeService.class);

    /**
     * 查询卖家已卖出的交易数据（根据创建时间）
     * @return
     */
    public TradesSoldGetResponse getTradeSold(TradesSoldGetRequest req) throws Exception{
        try{
            TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                    Constants.TB_SANDBOX_APP_KEY,
                    Constants.TB_SANDBOX_APP_SECRET);
            req.setFields("tid,type,status,payment,orders,num," +
                    "num_iid,price,total_fee,created,pay_time,end_time,buyer_nick,service_orders,buyer_rate," +
                    "");

            req.setStatus("TRADE_FINISHED");
            TradesSoldGetResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    public Trade getTradeBuyerContactInfo(Long tradeId) throws Exception{
        try{
            TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                    Constants.TB_SANDBOX_APP_KEY,
                    Constants.TB_SANDBOX_APP_SECRET);
            TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
            req.setFields("tid,receiver_state,receiver_name,receiver_address,receiver_mobile," +
                    "receiver_town,receiver_city,receiver_district");
            req.setTid(tradeId);
            TradeFullinfoGetResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response.getTrade();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public void getTradeSnapshot(Long tradeId) throws Exception{
        try{
            TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                    Constants.TB_SANDBOX_APP_KEY,
                    Constants.TB_SANDBOX_APP_SECRET);
            TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
            req.setFields("tid,type,status,snapshot_url,orders,receiver_name,receiver_address,receiver_mobile");
            req.setTid(192585158051323L);
            TradeFullinfoGetResponse response = client.execute(req ,   Constants.TB_SANDBOX_SESSION_KEY);
            System.out.println(response.getTrade());
        }catch (Exception e){

        }
    }

    public static void main(String[] args) throws Exception {
        TradeService tradeService = new TradeService();
        tradeService.getTradeSnapshot(null);
        tradeService.getTradeBuyerContactInfo(192585158051323L);
    }
}
