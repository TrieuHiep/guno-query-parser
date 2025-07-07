package vn.guno.reporting.core;


public class Column {
    private GTable table;
    private String name;
    private String alias;

    public Column() {
    }

    public Column(GTable table, String name) {
        this.table = table;
        this.name = name;
    }

    public Column(GTable table, String name, String alias) {
        this.table = table;
        this.name = name;
        this.alias = alias;
    }

    public GTable getTable() {
        return table;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public void setTable(GTable table) {
        this.table = table;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
