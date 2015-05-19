package com.hr.system.manage.repository.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Joey
 * Date: 13-9-27
 * Time: 上午9:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class QueryExpression {

    public enum Relation {
        AND, OR;
    }

    public enum Operation {
        // 不等于；大于；小于；大于等于；小于等于；两者之间包含两者；两者之间不包含两者
        Equal,
        Like,
        NotEqual,
        GreaterThan,
        LessThan,
        GreaterThanEqual,
        LessThanEqual,
     //   BetweenAndEquals,
      //  BetweenAndNotEquals,
        In,
        NotIn;
    }

    public boolean add(QueryExpression exp) {
        return false;
    }

    public boolean remove(QueryExpression exp) {
        return false;
    }

    public List<QueryExpression> getChild() {
        return null;
    }

    public abstract String toSolrExp();
}
