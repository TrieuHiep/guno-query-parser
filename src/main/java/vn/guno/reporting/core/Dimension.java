package vn.guno.reporting.core;


public class Dimension {
    private Column column;

    public Dimension() {
    }

    public Dimension(Column column) {
        this.column = column;
    }

    public Column getColumn() { return column; }

    public void setColumn(Column column) {
        this.column = column;
    }
}
