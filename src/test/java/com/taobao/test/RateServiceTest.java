package com.taobao.test;

import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.service.RateService;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by star on 15/5/19.
 */
public class RateServiceTest {
    RateService rateService = new RateService();



    @Test
    public void TestSearchRate() throws Exception {
        TraderatesGetRequest request = new TraderatesGetRequest();
        request.setPageNo(1L);
        request.setPageSize(10L);
        request.setRateType("give");
        request.setRole("seller");
        TraderatesGetResponse response = rateService.searchRate(request);
        System.out.println(response);
    }
}
