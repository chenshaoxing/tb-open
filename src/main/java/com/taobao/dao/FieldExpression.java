package com.taobao.dao;

import java.util.List;


public class FieldExpression extends QueryExpression {
    private Object value;

    private String field;

    private Operation operation;

    private Object former;

    private Object latter;

    public FieldExpression(String field, Operation operation, Object value) {
        this.value = value;
        this.field = field;
        this.operation = operation;
    }

    public FieldExpression(String field, Operation operation, Object former, Object latter) {
        this.field = field;
        this.operation = operation;
        this.former = former;
        this.latter = latter;
    }

    @Override
    public String toSolrExp() {
        StringBuilder sb = new StringBuilder();
        if (operation == null || operation == Operation.Equal || operation == Operation.Like) {
            sb.append("c.").append(field).append(" like ").append("? ");
        } else if (operation == Operation.NotEqual) {
            sb.append("c.").append(field).append(" != ").append("? ");
        } else if (operation == Operation.GreaterThan) {
            sb.append("c.").append(field).append(" > ").append("? ");
        } else if (operation == Operation.LessThan) {
            sb.append("c.").append(field).append(" < ").append("? ");
        } else if (operation == Operation.GreaterThanEqual) {
            sb.append("c.").append(field).append(" >= ").append("? ");
        } else if (operation == Operation.LessThanEqual) {
            sb.append("c.").append(field).append(" <= ").append("? ");
        } else if(operation == Operation.In){
            List<Integer> list = (List)value;
            sb.append("c.").append(field).append(" in (");
            for(int i=0;i<list.size();i++){
                if(i != list.size()-1){
                    sb.append(list.get(i)+",");
                }else{
                    sb.append(list.get(i));
                }
            }
            sb.append(")");
        }
//        else if (operation == Operation.BetweenAndEquals) {
//            sb.append(field).append(" < ").append(former).append(" TO ").append(latter).append("]");
//        } else if (operation == Operation.BetweenAndNotEquals) {
//            sb.append(field).append(":{").append(former).append(" TO ").append(latter).append("}");
//        }
        return sb.toString();
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getFormer() {
        return former;
    }

    public void setFormer(Object former) {
        this.former = former;
    }

    public Object getLatter() {
        return latter;
    }

    public void setLatter(Object latter) {
        this.latter = latter;
    }
}
