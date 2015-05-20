package com.taobao.dao;


import com.hr.system.manage.repository.domain.BaseDomain;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * User: tili
 * Date: 7/17/13
 * Time: 5:50 PM
 */
@Transactional
@Service(value = "baseDao")
public class BasePersistenceImpl implements IBasePersistence {

    @Autowired
    private SessionFactory sessionFactory;


    public Session getEntityManager(){
        return sessionFactory.getCurrentSession();
    }



    @Override
    public <T extends BaseDomain> T save(T t) {
        if(t.getCreatedTime() == null){
            t.setCreatedTime(new Date());
        }
        t.setUpdatedTime(new Date());
        return (T)getEntityManager().merge(t);
    }

    @Override
    public <T extends BaseDomain> void saveNew(T t) {
        t.setUpdatedTime(new Date());
        t.setCreatedTime(new Date());
        getEntityManager().persist(t);
    }



    @Override
    public <T extends BaseDomain> T getEntityById(Class<T> clazz, T t) {
        return (T)getEntityManager().get(clazz,t.getId());
    }

    public <T extends BaseDomain> void removeEntity(T t) {
        getEntityManager().delete(t);
    }

    @Override
    public <T extends BaseDomain> void removeDetachedEntity(T t) {
        t = (T)getEntityManager().merge(t);
        getEntityManager().delete(t);
    }

    @Override
    public int removeEntityByField(Class<? extends BaseDomain> clazz, String fieldName, Object fieldValue) {
        String sql = "delete from " + clazz.getName() + " c where c." + fieldName + " = ?";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(0, fieldValue);
        return query.executeUpdate();
    }

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue, int count) {
        String sql = "select c from " + clazz.getName() + " c where c." + fieldName + " = ? order by c.updatedTime desc";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(0, fieldValue);
        query.setFirstResult(0);
        query.setMaxResults(count);
        return query.list();
    }

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz, String fieldName, Object fieldValue) {
        String sql = "select count(c) from " + clazz.getName() + " c where c." + fieldName + " = ?";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(0, fieldValue);
        return (Long) query.uniqueResult();
    }

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz, HashMap parameter) {
        String sql = "select count(c) from " + clazz.getName() + " c ";
        int position = 0;
        Object[] fieldValues = new Object[parameter != null ? parameter.size() : 0];
        if (parameter != null && parameter.size() > 0) {
            sql += " where ";
            for (Iterator iter = parameter.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry element = (Map.Entry) iter.next();
                Object strKey = element.getKey();
                sql += "c." + strKey + " = ?";
                if (position < parameter.size() - 1) sql += " and ";
                fieldValues[position] = parameter.get(strKey);
                position++;
            }
        }
        Query query = getEntityManager().createQuery(sql);

        if (parameter != null && parameter.size() > 0) {
            for (int i = 0; i < parameter.size(); i++) {
                query.setParameter((i), fieldValues[i]);
            }
        }

        return (Long) query.uniqueResult();
    }

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz) {
        String sql = "select count(c) from " + clazz.getName() + " c";
        Query query = getEntityManager().createQuery(sql);
        return (Long) query.uniqueResult();
    }


    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue, String orderField, int pageIndex, int pageSize) {
        String sql = "select c from " + clazz.getName() + " c where c." + fieldName + " = ? order by c." + orderField + " desc";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(0, fieldValue);

        int defaultPageSize = 10;
        int defaultPageIndex = 1;
        if (pageSize != 0) {
            defaultPageSize = pageSize;
        }
        if (pageIndex != 0) {
            defaultPageIndex = pageIndex;
        }
        if (pageIndex != 0 || pageSize != 0) {
            query.setFirstResult((defaultPageIndex - 1) * defaultPageSize);
            query.setMaxResults(pageSize);
        }


        return query.list();
    }

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue, String orderField, int count) {
        String sql = "select c from " + clazz.getName() + " c where c." + fieldName + " = ? order by c." + orderField + " desc";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(0, fieldValue);
        query.setFirstResult(0);
        query.setMaxResults(count);
        return query.list();
    }

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String orderField, int pageIndex, int pageSize) {
        String sql = "select c from " + clazz.getName() + " c order by c." + orderField + " desc";
        Query query = getEntityManager().createQuery(sql);

        int defaultPageSize = 10;
        int defaultPageIndex = 1;
        if (pageSize != 0) {
            defaultPageSize = pageSize;
        }
        if (pageIndex != 0) {
            defaultPageIndex = pageIndex;
        }
        if (pageIndex != 0 || pageSize != 0) {
            query.setFirstResult((defaultPageIndex - 1) * defaultPageSize);
            query.setMaxResults(pageSize);
        }

        return query.list();
    }




    @Override
    public <T extends BaseDomain> List<T> getAllEntities(Class<T> clazz) {
        String sql = "select c from " + clazz.getName() + " c  order by c.updatedTime desc";
        Query query = getEntityManager().createQuery(sql);
        return query.list();
    }

    @Override
    public <T extends BaseDomain> List<T> getAllEntities(Class<T> clazz, String orderby) {
        String sql = "select c from " + clazz.getName() + " c  order by c." + orderby + " desc";
        Query query = getEntityManager().createQuery(sql);
        return query.list();
    }

    @Override
    public <T extends BaseDomain> List<T> getEntitiesByFieldList(Class<T> clazz, Map<String, Object> fieldNameValueMap) {
        String sql = "select c from " + clazz.getName() + " c where ";
        Set<String> fieldNames = fieldNameValueMap.keySet();
        Iterator<String> iterator = fieldNames.iterator();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = iterator.next();
            if (i == 0) {
                sql = sql + " c." + fieldName + " = ?";
            } else {
                sql = sql + " and c." + fieldName + " = ?";
            }
        }
        sql = sql + " order by c.updatedTime desc";
        Query query = getEntityManager().createQuery(sql);
        iterator = fieldNames.iterator();
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = iterator.next();
            query.setParameter(i, fieldNameValueMap.get(fieldName));
        }
        return query.list();
    }


    @Override
    public <T extends BaseDomain> T getEntityByField(Class<T> clazz, String fieldName, Object fieldValue) {
        String sql = "select c from " + clazz.getName() + " c where c." + fieldName + " = ? order by c.updatedTime desc";
        Query query = getEntityManager().createQuery(sql);
        query.setParameter(0, fieldValue);
        List<T> ret = query.list();
        if (ret == null || ret.size() < 1) {
            return null;
        }
        //TODO how to deal with multiple record.
        return ret.get(0);
    }

    @Override
    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue) {
        String sql = "select c from " + clazz.getName() + " c where c." + fieldName + " = ? order by c.updatedTime desc";
        Query query = getEntityManager().createQuery(sql);
        //TODO need add data row limitation
        query.setParameter(0, fieldValue);
        return query.list();
    }

    @Override
    public <T extends Object> PageInfo<T> query(QueryExpression queryExpression, OrderBy orderBy, int currPage, int pageSize) throws Exception {
//        QueryExpression nestedExpression = new NestedExpression();
//        QueryExpression dealerIdExpression = new FieldExpression("dealerId", QueryExpression.Operation.Equal,DealerHolder.getDealerId());
//        nestedExpression.add(dealerIdExpression);
//        QueryExpression focusedByDealerExpression = new FieldExpression("vehicle.focusedByDealer", QueryExpression.Operation.Equal, true);
//        nestedExpression.add(focusedByDealerExpression);
//        if(request.isSetName()){
//            QueryExpression nameExpression = new FieldExpression("subscriber.name", QueryExpression.Operation.Equal,request.getName());
//            nestedExpression.add(nameExpression);
//        }
//        if(request.isSetMobile()){
//            QueryExpression mobileExpression = new FieldExpression("subscriber.mobile", QueryExpression.Operation.Equal,request.getMobile());
//            nestedExpression.add(mobileExpression);
//        }
//        if(request.isSetDealStatus()){
//            QueryExpression dealStatusExpression = new FieldExpression("dealType", QueryExpression.Operation.Equal,request.getDealStatus());
//            nestedExpression.add(dealStatusExpression);
//        }
//        if(request.isSetPlateNumber()){
//            QueryExpression platNoExpression = new FieldExpression("vehicle.platNo", QueryExpression.Operation.Equal,request.getPlateNumber());
//            nestedExpression.add(platNoExpression);
//        }
//        if(request.isSetReservationDate()){
//            String reachShopTime = request.getReservationDate();
//            Date startTime = DateConvert.stringConvertDate(reachShopTime + " 00:00:00", "yyyy-MM-dd hh:mm:ss");
//            Date endTime = DateConvert.stringConvertDate(reachShopTime + " 23:59:59", "yyyy-MM-dd hh:mm:ss");
//            QueryExpression reservationDateExpression = new FieldExpression("reservationDate", QueryExpression.Operation.BetweenAndEquals,startTime,endTime);
//            nestedExpression.add(reservationDateExpression);
//        }
//        OrderBy orderBy = new OrderBy();
//        orderBy.setOrderField("reservationDate");
//        orderBy.setOrderType(OrderBy.OrderType.asc);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }




    public <T extends BaseDomain> PageInfo<T> getEntitiesByExpressions(Class<T> clazz, List<Expression> expressions, String orderField,int pageIndex, int pageSize) {
        String sql = "select c from " + clazz.getName() + " c ";
        if (expressions != null && expressions.size() > 0) {
            sql += " where ";
            for (int i = 0; i < expressions.size(); i++) {
                Expression condition = expressions.get(i);
                if (i == 0) {
                    sql = sql + " c." + condition.getFiledName() + " " + condition.getOperation().getValue() + " ?";
                } else {
                    if (condition.getRelation() == null)
                        throw new IllegalArgumentException("more than one expression you must be set relation value");
                    sql = sql + " " + condition.getRelation().getValue() + " c." + condition.getFiledName() + " " + condition.getOperation().getValue() + " ?";
                }
            }
        }
//        sql = sql + " order by c.updatedTime desc";

        if(orderField !=null && orderField.length() > 0){
            sql += " order by c."+orderField+" desc";
        } else {
            sql += " order by c.updatedTime desc";
        }

        Query query = getEntityManager().createQuery(sql);
        if (expressions != null && expressions.size() > 0) {
            for (int i = 0; i < expressions.size(); i++) {
                Expression condition = expressions.get(i);
                switch (condition.getOperation()) {
                    case AllLike:
                        query.setParameter(i, "%" + condition.getFiledValue() + "%");
                        break;
                    case LeftLike:
                        query.setParameter(i, "%" + condition.getFiledValue());
                        break;
                    case RightLike:
                        query.setParameter(i, condition.getFiledValue() + "%");
                        break;
                    default:
                        query.setParameter(i, condition.getFiledValue());
                        break;
                }

            }
        }
        query.setFirstResult((pageIndex - 1) * pageSize);
        query.setMaxResults(pageSize);

        int totalNum = (int) getEntitiesByExpressionsTotalCount(clazz, expressions);
        PageInfo<T> pageInfo = new PageInfo<T>(pageSize, totalNum);
        pageInfo.setList(query.list());
        return pageInfo;
    }

    public <T extends BaseDomain> long getEntitiesByExpressionsTotalCount(Class<T> clazz, List<Expression> expressions) {
        String sql = "select count(c) from " + clazz.getName() + " c ";
        if (expressions != null && expressions.size() > 0) {
            sql += " where ";
            for (int i = 0; i < expressions.size(); i++) {
                Expression condition = expressions.get(i);
                if (i == 0) {
                    sql = sql + " c." + condition.getFiledName() + " " + condition.getOperation().getValue() + " ?";
                } else {
                    if (condition.getRelation() == null)
                        throw new IllegalArgumentException("more than one expression you must be set relation value");
                    sql = sql + " " + condition.getRelation().getValue() + " c." + condition.getFiledName() + " " + condition.getOperation().getValue() + " ?";
                }
            }
        }
        //sql = sql+ " order by c.updatedTime desc";
        Query query = getEntityManager().createQuery(sql);
        if (expressions != null && expressions.size() > 0) {
            for (int i = 0; i < expressions.size(); i++) {
                Expression condition = expressions.get(i);
                switch (condition.getOperation()) {
                    case AllLike:
                        query.setParameter(i, "%" + condition.getFiledValue() + "%");
                        break;
                    case LeftLike:
                        query.setParameter(i, "%" + condition.getFiledValue());
                        break;
                    case RightLike:
                        query.setParameter(i, condition.getFiledValue() + "%");
                        break;
                    default:
                        query.setParameter(i, condition.getFiledValue());
                        break;
                }
            }
        }
        try{
            List result = query.list();
            if(result != null && result.size() > 0) {
                return (Long) result.get(0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0l;
    }







}
