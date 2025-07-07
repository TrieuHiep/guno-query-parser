package vn.guno.reporting.core;


import vn.guno.reporting.global.Operator;

public class Condition implements BaseCondition{
    private Column column;
    private Operator operator;
    private Object value;

    public Condition(Column column, Operator operator, Object value) {
        this.column = column;
        this.operator = operator;
        this.value = value;
    }

    public Column getColumn() { return column; }
    public Operator getOperator() { return operator; }
    public Object getValue() { return value; }

    public void setColumn(Column column) {
        this.column = column;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
