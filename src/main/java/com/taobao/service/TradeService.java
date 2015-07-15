package com.taobao.service;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Shipping;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import com.taobao.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource(name = "taoBaoClient")
    private TaobaoClient taobaoClient;

    private static final Logger LOG = LoggerFactory.getLogger(TradeService.class);

    public Trade getTradeInfo(Long tid,String sessionKey) throws Exception{
        try{
            TradeGetRequest req=new TradeGetRequest();
            req.setFields("tid,type,num_iid,status,payment,orders,num");
            req.setTid(tid);
            TradeGetResponse response = taobaoClient.execute(req , sessionKey);
            return response.getTrade();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }

    }


    /**
     * 查询卖家已卖出的交易数据（根据创建时间）
     * @return
     */
    public TradesSoldGetResponse getTradeSold(TradesSoldGetRequest req) throws Exception{
        try{
            req.setFields("tid,type,status,payment,orders,num," +
                    "num_iid,price,total_fee,created,pay_time,end_time,buyer_nick,service_orders,buyer_rate");

            req.setStatus("TRADE_FINISHED");
            TradesSoldGetResponse response = taobaoClient.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 查询卖家已卖出的交易数据（根据创建时间）
     * @return
     */
    public TradesSoldGetResponse getTradeSold(TradesSoldGetRequest req,String sessionKey) throws Exception{
        try{
            req.setFields("tid,oid,type,status,payment,orders,num," +
                    "num_iid,price,total_fee,created,pay_time,end_time,buyer_nick,service_orders,buyer_rate");

            req.setStatus("TRADE_FINISHED");
            TradesSoldGetResponse response = taobaoClient.execute(req , sessionKey);
            return response;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    public Trade getTradeBuyerContactInfo(Long tradeId) throws Exception{
        try{
            TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
            req.setFields("tid,receiver_state,receiver_name,receiver_address,receiver_mobile," +
                    "receiver_town,receiver_city,receiver_district");
            req.setTid(tradeId);
            TradeFullinfoGetResponse response = taobaoClient.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response.getTrade();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public Trade getTradeBuyerContactInfo(Long tradeId,String sessionKey) throws Exception{
        try{
            TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
            req.setFields("tid,receiver_state,receiver_name,receiver_address,receiver_mobile," +
                    "receiver_town,receiver_city,receiver_district");
            req.setTid(tradeId);
            TradeFullinfoGetResponse response = taobaoClient.execute(req , sessionKey);
            return response.getTrade();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public Shipping getExpressInfo(Long tid,String sessionKey) throws Exception{
        try{
            LogisticsOrdersDetailGetRequest req=new LogisticsOrdersDetailGetRequest();
            req.setTid(tid);
            req.setPageNo(1L);
            req.setPageSize(10L);
            req.setFields("tid,seller_nick,buyer_nick,out_sid,receiver_name,receiver_mobile,receiver_phone,receiver_location,company_name,item_title,sub_tids");
            LogisticsOrdersDetailGetResponse response = taobaoClient.execute(req , sessionKey);
            if(response.getShippings()  != null){
                return response.getShippings().get(0);
            }
            return null;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


//    public void getTradeSnapshot(Long tradeId) throws Exception{
//        try{
//            TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
//                    Constants.TB_SANDBOX_APP_KEY,
//                    Constants.TB_SANDBOX_APP_SECRET);
//            TradeFullinfoGetRequest req=new TradeFullinfoGetRequest();
//            req.setFields("tid,type,status,snapshot_url,orders,receiver_name,receiver_address,receiver_mobile");
//            req.setTid(192585158051323L);
//            TradeFullinfoGetResponse response = client.execute(req ,   Constants.TB_SANDBOX_SESSION_KEY);
//            System.out.println(response.getTrade());
//        }catch (Exception e){
//
//        }
//    }

    public static void main(String[] args) throws Exception {
//        TradeService tradeService = new TradeService();
//        tradeService.getTradeSnapshot(null);
//        tradeService.getTradeBuyerContactInfo(192585158051323L);
    }
}
