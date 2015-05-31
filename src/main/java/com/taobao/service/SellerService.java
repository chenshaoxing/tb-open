package com.taobao.service;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.User;
import com.taobao.api.request.UserSellerGetRequest;
import com.taobao.api.response.UserSellerGetResponse;
import com.taobao.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-05-21
 * Time: 9:54
 * function:
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SellerService {
    public static final Logger LOG = LoggerFactory.getLogger(SellerService.class);

    @Resource(name = "taoBaoClient")
    private TaobaoClient taobaoClient;

    public User getSellerInfo() throws Exception{
        try{
            TaobaoClient client=new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                    Constants.TB_SANDBOX_APP_KEY,
                    Constants.TB_SANDBOX_APP_SECRET);
            UserSellerGetRequest req=new UserSellerGetRequest();
            req.setFields("nick,avatar");
            UserSellerGetResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response.getUser();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    public User getSellerInfo(String sessionKey) throws Exception{
        try{
            UserSellerGetRequest req=new UserSellerGetRequest();
            req.setFields("nick,avatar");
            UserSellerGetResponse response = taobaoClient.execute(req ,sessionKey);
            return response.getUser();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }
}
