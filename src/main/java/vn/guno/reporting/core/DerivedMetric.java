package vn.guno.reporting.core;


public class DerivedMetric {
    private String alias;
    private String expression; // raw SQL expression, e.g. "SUM(clicks) / NULLIF(SUM(impressions), 0)"

    public DerivedMetric() {
    }

    public DerivedMetric(String alias, String expression) {
        this.alias = alias;
        this.expression = expression;
    }

    public String getAlias() {
        return alias;
    }

    public String getExpression() {
        return expression;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
