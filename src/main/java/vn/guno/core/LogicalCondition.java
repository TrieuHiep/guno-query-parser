package vn.guno.core;



import vn.guno.global.LogicalOperator;

import java.util.List;

public class LogicalCondition implements BaseCondition {
    private LogicalOperator operator; // AND or OR
    private List<BaseCondition> conditions; // Can be Condition or LogicalCondition (for complex condition)

    public LogicalOperator getOperator() {
        return operator;
    }

    public void setOperator(LogicalOperator operator) {
        this.operator = operator;
    }

    public List<BaseCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<BaseCondition> conditions) {
        this.conditions = conditions;
    }
}
