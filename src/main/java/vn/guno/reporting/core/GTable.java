package vn.guno.reporting.core;


import vn.guno.reporting.sub.SubquerySource;

import java.util.ArrayList;
import java.util.List;

public class GTable {
    private String name;
    private String alias;
    private List<Column> columns = new ArrayList<>();

    private SubquerySource subquery; // optional

    public boolean isSubquery() {
        return subquery != null && subquery.getQuery() != null;
    }

    public GTable(String name, String alias) {
        this.name = name;
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void addColumn(Column column) {
        columns.add(column);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public SubquerySource getSubquery() {
        return subquery;
    }

    public void setSubquery(SubquerySource subquery) {
        this.subquery = subquery;
    }
}
