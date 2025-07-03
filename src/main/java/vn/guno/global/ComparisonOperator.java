package vn.guno.global;

public enum ComparisonOperator {
    EQUALS("="),
    NOT_EQUALS("!="),
    GREATER_THAN(">"),
    LESS_THAN("<"),
    GREATER_EQUAL(">="),
    LESS_EQUAL("<=");

    private final String sqlOperator;

    ComparisonOperator(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String getSqlOperator() { return sqlOperator; }
}
