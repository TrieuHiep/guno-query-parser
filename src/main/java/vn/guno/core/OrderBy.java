package vn.guno.core;


import vn.guno.global.SortDirection;

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
