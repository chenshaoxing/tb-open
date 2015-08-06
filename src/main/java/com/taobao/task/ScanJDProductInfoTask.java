package com.taobao.task;

import com.taobao.common.Constants;
import com.taobao.dao.PageInfo;
import com.taobao.entity.JDProduct;
import com.taobao.entity.TaoBaoProduct;
import com.taobao.entity.TbRelationJd;
import com.taobao.entity.User;
import com.taobao.service.*;
import org.apache.commons.lang.StringUtils;
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
    @Resource(name = "onlineEmailService")
    private OnlineEmailService emailService;

    private void refreshSession(){
        List<User> userList = userService.findAll();
        for(User u:userList){
            sessionKeyService.refreshSessionKeyAndSave(u);
        }
    }


    public void scan(){
        try{
//            refreshSession();
            LOG.info("start ScanJDProductInfoTask ");
            int currentPage = 1;
            PageInfo pageInfo = jdProductService.getList(currentPage, 10);
            int pageNum = Integer.valueOf(pageInfo.getPageTotalNum()+"");
            StringBuffer content = new StringBuffer("");
            for(int i = 1;i<=pageNum;i++){
                pageInfo = jdProductService.getList(i,100);
                List<JDProduct> jdProductList = pageInfo.getList();
                for(JDProduct jd:jdProductList){
                    Map<String,Object> result = crawlJdProductInfoService.crawl(jd.getSkuid());
                    if(result != null){
                        float price = Float.valueOf(result.get("price").toString());
                        BigDecimal latestPrice = new BigDecimal(price+"");
                        BigDecimal afterPrice = new BigDecimal(jd.getPrice());
                        if(price < 1f){
                            continue;
                        }
                        jd.setPrice(price);
                        jd.setDiscount(Float.valueOf(result.get("discount").toString()));
                        jd.setName(result.get("name").toString());
                        jdProductService.save(jd);
                        List<TbRelationJd> listTb = tbRelationJdService.findByJdSkuId(jd.getSkuid());
                        for(TbRelationJd tj:listTb){
                            TaoBaoProduct tp = tj.getProduct();
                            BigDecimal tbPrice = new BigDecimal(tp.getPrice().toString());
                            BigDecimal latestDifPrice = tbPrice.subtract(latestPrice);
                            BigDecimal diffPrice = new BigDecimal(tp.getDifferenceOfPrices().toString());
                            if(latestDifPrice.compareTo(diffPrice) != 0){
                                BigDecimal tbLatestPrice = latestPrice.add(diffPrice);
                                tbLatestPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
                                tp.setPrice(tbLatestPrice.floatValue());
                                boolean flag = taoBaoProductInfoService.updateProductPrice(tp,tp.getUser().getSessionKey());
//                                    boolean flag = taoBaoProductInfoService.updateProductPrice(tp,tp.getUser().getSessionKey());
                                if(flag){

                                    content.append("商品:"+jd.getName()+" 最新价格:"+jd.getPrice()+" 已经超过您设置的淘宝差价," +
                                            "您对应的淘宝商品价格已经修改为:"+tbLatestPrice.floatValue()+" 点击此链接进行查看:"+ Constants.TAO_BAO_ITEM_URL_PREFIX+tp.getNumIid());
                                    content.append("<br/><br/>");
                                    taoBaoProductService.add(tp);
                                }
                            }
                        }
                    }
                }
            }
            LOG.info("end ScanJDProductInfoTask ");
            emailService.sendEmail(Constants.ADMIN_EMAIL,"淘宝价格修改",content.toString());
        }catch (Exception e){
            LOG.error(e.getMessage());
        }
    }
}
