package vn.guno.reporting;

import java.util.List;

public class QueryResult {
    private final String sql;
    private final List<Object> bindValues;

    public QueryResult(String sql, List<Object> bindValues) {
        this.sql = sql;
        this.bindValues = bindValues;
    }

    public String getSql() { return sql; }
    public List<Object> getBindValues() { return bindValues; }
}
