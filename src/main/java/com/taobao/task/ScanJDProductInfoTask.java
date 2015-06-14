package com.taobao.task;

import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;
import com.taobao.entity.TaoBaoProduct;
import com.taobao.entity.TbRelationJd;
import com.taobao.entity.User;
import com.taobao.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with Intellij IDEA
 * User: star
 * Date: 2015-06-08
 * Time: 15:41
 * function:
 * To change this template use File | Settings | File Templates.
 */
public class ScanJDProductInfoTask {
    private static final Logger LOG = LoggerFactory.getLogger(ScanJDProductInfoTask.class);

    @Resource
    private JDProductService jdProductService;
    @Resource
    private CrawlJdProductInfoService crawlJdProductInfoService;
    @Resource
    private TaoBaoProductService taoBaoProductService;
    @Resource
    private TaoBaoProductInfoService taoBaoProductInfoService;
    @Resource
    private UserService userService;
    @Resource
    private SessionKeyService sessionKeyService;
    @Resource
    private TbRelationJdService tbRelationJdService;


    private void refreshSession(){
        List<User> userList = userService.findAll();
        for(User u:userList){
            sessionKeyService.refreshSessionKeyAndSave(u);
        }
    }


    public void scan(){
        try{
            refreshSession();
            int currentPage = 1;
            PageInfo pageInfo = jdProductService.getList(currentPage, 10);
            int pageNum = Integer.valueOf(pageInfo.getPageTotalNum()+"");
            for(int i = 1;i<=pageNum;i++){
                pageInfo = jdProductService.getList(i,100);
                List<JDProduct> jdProductList = pageInfo.getList();
                for(JDProduct jd:jdProductList){
                    Map<String,Object> result = crawlJdProductInfoService.crawl(jd.getSkuid());
                    if(result != null){
                        Float price = Float.valueOf(result.get("price").toString());
                        BigDecimal latestPrice = new BigDecimal(price);
                        BigDecimal afterPrice = new BigDecimal(jd.getPrice());
                        if(latestPrice.compareTo(afterPrice) != 0){
                            jd.setPrice(price);
                            jd.setDiscount(Float.valueOf(result.get("discount").toString()));
                            jd.setName(result.get("name").toString());
                            jdProductService.save(jd);
                            List<TbRelationJd> listTb = tbRelationJdService.findByJdSkuId(jd.getSkuid());
                            for(TbRelationJd tj:listTb){
                                TaoBaoProduct tp = tj.getProduct();
                                BigDecimal tbPrice = new BigDecimal(tp.getPrice());
                                BigDecimal abs = latestPrice.subtract(tbPrice).abs();
                                BigDecimal diffPrice = new BigDecimal(tp.getDifferenceOfPrices());
                                if(abs.compareTo(diffPrice) != 0){
                                    BigDecimal tbLatestPrice = latestPrice.subtract(diffPrice);
                                    tbLatestPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                                    tp.setPrice(tbLatestPrice.floatValue());
                                    boolean flag = taoBaoProductInfoService.updateProductPrice(tp,tp.getUser().getSessionKey());
//                                    boolean flag = taoBaoProductInfoService.updateProductPrice(tp,tp.getUser().getSessionKey());
                                    if(flag){
                                        taoBaoProductService.add(tp);
                                    }
                                }

                            }
//                            Set<TaoBaoProduct> taoBaoProducts =  jd.getTaoBaoProducts();
//                            for(TaoBaoProduct tb:taoBaoProducts){
//                               tb.setPrice(price+tb.getDifferenceOfPrices());

//                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }
}
