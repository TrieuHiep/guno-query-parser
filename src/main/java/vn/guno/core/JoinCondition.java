package vn.guno.core;

import vn.guno.global.ComparisonOperator;

public class JoinCondition {
    private Column leftColumn;         //  Proper Column object
    private Column rightColumn;        //  Proper Column object
    private ComparisonOperator operator; //  Dedicated enum

    public JoinCondition() {
    }

    public JoinCondition(Column left, Column right, ComparisonOperator op) {
        this.leftColumn = left;
        this.rightColumn = right;
        this.operator = op;
    }

    // Type-safe validation
    public boolean validate() {
        return leftColumn != null &&
                rightColumn != null &&
                operator != null;
    }

    // Clean SQL generation
    public String toSQL() {
        return String.format("%s.%s %s %s.%s",
                leftColumn.getTable().getAlias(),
                leftColumn.getName(),
                operator.getSqlOperator(),
                rightColumn.getTable().getAlias(),
                rightColumn.getName()
        );
    }

    public Column getLeftColumn() {
        return leftColumn;
    }

    public void setLeftColumn(Column leftColumn) {
        this.leftColumn = leftColumn;
    }

    public Column getRightColumn() {
        return rightColumn;
    }

    public void setRightColumn(Column rightColumn) {
        this.rightColumn = rightColumn;
    }

    public ComparisonOperator getOperator() {
        return operator;
    }

    public void setOperator(ComparisonOperator operator) {
        this.operator = operator;
    }
}
