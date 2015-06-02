package com.taobao.service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TmcUserPermitRequest;
import com.taobao.api.response.TmcUserPermitResponse;
import com.taobao.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by star on 15/5/17.
 * 用户授权【Permit】应用获取消息
 调用taobao.tmc.user.permit接口用户授权应用获取消息
 */
@Service
public class UserPermitService {
    private static final Logger LOG = LoggerFactory.getLogger(UserPermitService.class);

    @Resource(name = "taoBaoClient")
    private TaobaoClient taobaoClient;

    /**
     *
     * @throws ApiException
     */
    public void userPermit() throws ApiException {
        TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL, Constants.TB_SANDBOX_APP_KEY,Constants.TB_SANDBOX_APP_SECRET);
        TmcUserPermitRequest req=new TmcUserPermitRequest();
        req.setTopics("taobao_trade_TradeRated,taobao_trade_TradeSuccess");
//        req.setTopics("taobao_trade_TradeSuccess,taobao_trade_TradeCreate,taobao_trade_TradeBuyerPay,taobao_trade_TradeRated");
        TmcUserPermitResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
        System.out.println(response.getBody());
    }

    /**
     *
     * @throws ApiException
     */
    public boolean userPermit(String sessionKey) throws Exception {
        try{
            TmcUserPermitRequest req=new TmcUserPermitRequest();
            req.setTopics("taobao_trade_TradeRated,taobao_trade_TradeSuccess");
//        req.setTopics("taobao_trade_TradeSuccess,taobao_trade_TradeCreate,taobao_trade_TradeBuyerPay,taobao_trade_TradeRated");
            TmcUserPermitResponse response = taobaoClient.execute(req ,sessionKey);
            return response.isSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }

    }

    public static void main(String[] args) {

    }

}
