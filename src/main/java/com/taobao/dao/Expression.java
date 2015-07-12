package com.taobao.dao;

public class Expression {

    public enum Operation {
        Equal("="),
        NotEqual("<>"),
        GreaterThan(">"),
        LessThan("<"),
        GreaterThanEqual(">="),
        LessThanEqual("<="),
        IN("in"),
        NotIN("not in"),
        LeftLike("like"),
        RightLike("like"),
        AllLike("like"),
        IS("is");
        ;

        private String value;

        Operation(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


    public enum Relation {
        AND("and"),
        OR("or");

        private String value;

        Relation(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private String filedName;  //查询字段名称

    private Object filedValue; //查询值

    private Relation relation; //查询关系 and or

    private Operation operation; //筛选操作 =、<=等

    public Expression() {
    }

    public Expression(String filedName, Object filedValue, Relation relation, Operation operation) {
        this.relation = relation;
        this.filedName = filedName;
        this.filedValue = filedValue;
        this.operation = operation;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public Object getFiledValue() {
        return filedValue;
    }

    public void setFiledValue(Object filedValue) {
        this.filedValue = filedValue;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expression that = (Expression) o;

        if (filedName != null ? !filedName.equals(that.filedName) : that.filedName != null) return false;
        if (filedValue != null ? !filedValue.equals(that.filedValue) : that.filedValue != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filedName != null ? filedName.hashCode() : 0;
        result = 31 * result + (filedValue != null ? filedValue.hashCode() : 0);
        return result;
    }
}
