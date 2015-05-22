package com.taobao.service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TraderateAddRequest;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderateAddResponse;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Created by star on 15/5/17.
 * 评价服务接口
 */
@Service
public class RateService {
    public static final Logger LOG = LoggerFactory.getLogger(RateService.class);

    public enum RateEnum{

        GOOD("good"),NEUTRAL("neutral"),BAD("bad");

        private String value;

        private RateEnum(String value) {
            this.value = value;
        }

        public String getValue(){
            return this.value;
        }

    }

    /**
     * 评价单个订单（不包含子订单）
     * @param tid
     * @param rateEnum
     * @param content
     * @return
     * @throws Exception
     */
    public boolean add(Long tid,RateEnum rateEnum,String content) throws Exception {
        try {
            TaobaoClient client = new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                    Constants.TB_SANDBOX_APP_KEY,Constants.TB_SANDBOX_APP_SECRET);
            TraderateAddRequest request = new TraderateAddRequest();
            request.setTid(tid);
            request.setContent(content);
            request.setRole("seller");
            request.setAnony(true);
            request.setResult(rateEnum.getValue());
            TraderateAddResponse response = client.execute(request,Constants.TB_SANDBOX_SESSION_KEY);
            return  response.isSuccess();
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 评价单个订单（含子订单）
     * @param tid
     * @param rateEnum
     * @param content
     * @return
     * @throws Exception
     */
    public boolean add(Long tid,Long oid,RateEnum rateEnum,String content) throws Exception {
        try {
            TaobaoClient client = new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                    Constants.TB_SANDBOX_APP_KEY,Constants.TB_SANDBOX_APP_SECRET);
            TraderateAddRequest request = new TraderateAddRequest();
            request.setTid(tid);
            request.setOid(oid);
            request.setContent(content);
            request.setRole("seller");
            request.setAnony(true);
            request.setResult(rateEnum.getValue());
            TraderateAddResponse response = client.execute(request,Constants.TB_SANDBOX_SESSION_KEY);
            response.isSuccess();
            return false;
        }catch (Exception e){
            LOG.error(e.getMessage());
            throw e;
        }
    }


    /**
     *
     * @param req
     * @throws Exception
     */
    public TraderatesGetResponse searchRate(TraderatesGetRequest req) throws Exception {
        TaobaoClient client = new DefaultTaobaoClient(Constants.TB_SANDBOX_API_URL,
                Constants.TB_SANDBOX_APP_KEY,Constants.TB_SANDBOX_APP_SECRET);
        req.setFields("tid,oid,role,nick,result,created,rated_nick,item_title,item_price,content,reply,num_iid,valid_score");
        try {
            TraderatesGetResponse response = client.execute(req , Constants.TB_SANDBOX_SESSION_KEY);
            return response;
        } catch (ApiException e) {
            LOG.error(e.getMessage());
            throw e;
        }
    }

}
