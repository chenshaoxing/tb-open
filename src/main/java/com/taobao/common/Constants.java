package com.taobao.common;

/**
 * Created by star on 15/5/17.
 */
public class Constants {
    public static final String TB_SANDBOX_API_URL = "http://gw.api.tbsandbox.com/router/rest";
    public static final String TB_SANDBOX_APP_KEY = "1023175152";
    public static final String TB_SANDBOX_APP_SECRET = "sandbox9e3c1e8c65b37da4d0237a953";

    public static final String TB_SANDBOX_SESSION_KEY = "6102917987b523e06b6565087165bd84a3be5fe4b699a492074082787";
    public static final String TB_SANDBOX_MESSAGE_URL = "ws://mc.api.tbsandbox.com/";


    public static final String TB_ONLINE_API_URL = "http://gw.api.taobao.com/router/rest";
    public static final String TB_ONLINE_APP_KEY = "23175152";
    public static final String TB_ONLINE_APP_SECRET = "fe3900d9e3c1e8c65b37da4d0237a953";
    public static final String TB_ONLINE_MESSAGE_URL = "ws://mc.api.taobao.com/";
    /*交易成功消息*/
    public static final String TAOBAO_TRADE_TRADESUCCESS ="taobao_trade_TradeSuccess";
    /*评价变更消息*/
    public static final String TAOBAO_TRADE_TRADERATED = "taobao_trade_TradeRated";

    public static final String BEFORE_OVERTIME_EXEC_RATE_DATE = "before.overtime.exec.rate.date";
    public static final String GLOBAL_ADMIN_EMAIL="global.admin.email";
    public static final String USER_OVER_TIME_REMIND_DAY="user.over.time.remind.day";
    public static final String AUTO_RATE_LOG_DELETE_DAY = "auto.rate.log.delete.day";

    public static final String ADMIN_EMAIL = "45388540@qq.com";

    public static final Long BLACK_LIST_NUM = 500L;

//    public static final String COOKIE_CIPHER_KEY = "cookie.cipher.key";
    public static final String COOKIE_CIPHER_KEY = "3ac0bb4abf5b2b5113c350f0ee856a15";
    public static final String LOG_URL = "https://oauth.taobao.com/authorize?response_type=code&client_id=23175152&redirect_uri=http://120.25.250.133:8080/auth&state=1212&view=web";
    public static final String LOGOUT_URL = "https://oauth.taobao.com/logoff?client_id=23175152&view=web";


    public static final String  TAOBAO_TRADERATES_GET_TID_NO_EXIST ="isv.invalid-parameter:tid";
    public static final String TAOBAO_TRADES_SOLD_GET_BUYER_NO_EXIST = "isv.invalid-parameter:buyer_nick";
    public static final String BROWSE_URL = "browse.url";
}
