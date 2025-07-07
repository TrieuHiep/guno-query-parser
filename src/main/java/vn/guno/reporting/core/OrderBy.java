package vn.guno.reporting.core;


import vn.guno.reporting.global.SortDirection;

public class OrderBy {
    private Column column;
    private SortDirection direction;

    public OrderBy(Column column, SortDirection direction) {
        this.column = column;
        this.direction = direction;
    }

    public Column getColumn() { return column; }
    public SortDirection getDirection() { return direction; }

    public void setColumn(Column column) {
        this.column = column;
    }

    public void setDirection(SortDirection direction) {
        this.direction = direction;
    }
}
