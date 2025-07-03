package vn.guno.sub;


import vn.guno.ReportQuery;

public class SubquerySource {
    private String alias;
    private ReportQuery query; // instead of raw SQL

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public ReportQuery getQuery() {
        return query;
    }

    public void setQuery(ReportQuery query) {
        this.query = query;
    }
}
