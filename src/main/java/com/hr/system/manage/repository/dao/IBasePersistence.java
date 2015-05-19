package com.hr.system.manage.repository.dao;


import com.hr.system.manage.repository.domain.BaseDomain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IBasePersistence {


    public <T extends BaseDomain> T save(T t);

    public <T extends BaseDomain> void saveNew(T t);


    public <T extends BaseDomain> T getEntityById(Class<T> clazz, T t);

    public <T extends BaseDomain> void removeEntity(T t);

    public int removeEntityByField(Class<? extends BaseDomain> clazz, String fieldName, Object fieldValue);

    public <T extends BaseDomain> void removeDetachedEntity(T t);

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue, int count);

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue, String orderField, int pageIndex, int pageSize);

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue, String orderField, int count);

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String orderField, int pageIndex, int pageSize);

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz, String fieldName, Object fieldValue);

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz, HashMap parameter);

    public <T extends BaseDomain> long getEntityTotalCount(Class<T> clazz);




    public <T extends BaseDomain> List<T> getAllEntities(Class<T> clazz);

    public <T extends BaseDomain> List<T> getAllEntities(Class<T> clazz, String orderby);

    public <T extends BaseDomain> List<T> getEntitiesByFieldList(Class<T> clazz, Map<String, Object> fieldNameValueMap);




    public <T extends BaseDomain> T getEntityByField(Class<T> clazz, String fieldName, Object fieldValue);

    public <T extends BaseDomain> List<T> getEntitiesByField(Class<T> clazz, String fieldName, Object fieldValue);


    public <T extends Object> PageInfo<T> query(QueryExpression queryExpression, OrderBy orderBy, int currPage, int pageSize) throws Exception;



    public <T extends BaseDomain> PageInfo<T> getEntitiesByExpressions(Class<T> clazz, List<Expression> conditions, String orderField, int pageIndex, int pageSize);

    /**
     *
     * @param clazz
     * @param conditions
     * @param <T>
     * @return
     */
    public <T extends BaseDomain> long getEntitiesByExpressionsTotalCount(Class<T> clazz, List<Expression> conditions);

}
