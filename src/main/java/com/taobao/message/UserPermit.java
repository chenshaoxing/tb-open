package com.taobao.message;

import com.taobao.api.ApiException;

/**
 * Created by star on 15/5/17.
 * 用户授权【Permit】应用获取消息
 调用taobao.tmc.user.permit接口用户授权应用获取消息
 */
public class UserPermit {
    public static void main(String[] args) throws ApiException {
        UserPermit u  = new UserPermit();
//        u.userPermit();
//        u.get();
    }

//    public void userPermit() throws ApiException {
//        TaobaoClient client=new DefaultTaobaoClient("http://gw.api.tbsandbox.com/router/rest", "1023175152", "sandbox9e3c1e8c65b37da4d0237a953");
//
//        TmcUserPermitRequest req=new TmcUserPermitRequest();
//        req.setTopics("taobao_trade_TradeSuccess,taobao_trade_TradeCreate,taobao_trade_TradeBuyerPay");
//
//        TmcUserPermitResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
//        System.out.println(response.getBody());
//    }
//
//    public void get() throws ApiException {
//        String appKey = Constants.TB_SANDBOX_APP_KEY;
//        String appSecret = "sandbox9e3c1e8c65b37da4d0237a953";
//        String serverUrl = "http://gw.api.tbsandbox.com/router/rest";
//
//        TaobaoClient client2 = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
//        TmcUserGetRequest requser = new TmcUserGetRequest();
//        requser.setFields("user_nick,topics,user_id,is_valid,created,modified");
//        requser.setNick("sandbox_c_2"); //店铺昵称
//        TmcUserGetResponse response = client2.execute(requser);
//        System.out.println(response.getTmcUser());
//    }
}