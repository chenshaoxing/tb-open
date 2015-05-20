package com.taobao.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Joey
 * Date: 13-9-27
 * Time: 上午9:45
 * To change this template use File | Settings | File Templates.
 */
public class NestedExpression extends QueryExpression {
    private List<QueryExpression> queryExpressionList;

    private Relation relation = Relation.AND;

    public NestedExpression() {
        this.queryExpressionList = new ArrayList<QueryExpression>();
    }

    public NestedExpression(Relation relation) {
        this.relation = relation;
        this.queryExpressionList = new ArrayList<QueryExpression>();
    }

    @Override
    public boolean add(QueryExpression exp) {
        return queryExpressionList.add(exp);
    }

    @Override
    public boolean remove(QueryExpression exp) {
        return queryExpressionList.remove(exp);
    }

    @Override
    public List<QueryExpression> getChild() {
        return this.queryExpressionList;
    }

    @Override
    public String toSolrExp() {
        StringBuilder sb = new StringBuilder();
        for (Iterator<QueryExpression> i = this.queryExpressionList.iterator(); i.hasNext();){
            QueryExpression exp = i.next();
            if (exp instanceof NestedExpression){
                sb.append("(" + exp.toSolrExp() + ")");
            }else{
                sb.append(exp.toSolrExp());
            }
            if (i.hasNext()){
                sb.append(" " + relation.name() + " ");
            }
        }
        return sb.toString();
    }
}
