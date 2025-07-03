package vn.guno.core;


import vn.guno.global.AggregationType;

public class Metric {
    private AggregationType type;
    private Column column;
    private String alias;

    public Metric() {
    }

    public Metric(AggregationType type, Column column, String alias) {
        this.type = type;
        this.column = column;
        this.alias = alias;
    }

    public AggregationType getType() {
        return type;
    }

    public Column getColumn() {
        return column;
    }

    public String getAlias() {
        return alias;
    }

    public void setType(AggregationType type) {
        this.type = type;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
