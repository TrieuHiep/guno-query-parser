package vn.guno.reporting.core;


import vn.guno.reporting.global.JoinType;
import vn.guno.reporting.sub.SubquerySource;

import java.util.ArrayList;
import java.util.List;

public class Join {
    private GTable fromTable;
    private GTable toTable;
    private JoinType joinType;
    private JoinCondition onCondition;
    private List<Join> nestedJoins = new ArrayList<>();

    private SubquerySource toSubquery; // For subquery join

    public GTable getFromTable() {
        return fromTable;
    }

    public Join() {
    }

    public void setFromTable(GTable fromGTable) {
        this.fromTable = fromGTable;
    }

    public GTable getToTable() {
        return toTable;
    }

    public void setToTable(GTable toGTable) {
        this.toTable = toGTable;
    }

    public JoinType getJoinType() {
        return joinType;
    }

    public void setJoinType(JoinType joinType) {
        this.joinType = joinType;
    }

//    public Condition getOnCondition() {
//        return onCondition;
//    }
//
//    public void setOnCondition(Condition onCondition) {
//        this.onCondition = onCondition;
//    }


    public JoinCondition getOnCondition() {
        return onCondition;
    }

    public void setOnCondition(JoinCondition onCondition) {
        this.onCondition = onCondition;
    }

    public List<Join> getNestedJoins() {
        return nestedJoins;
    }

    public void addNestedJoin(Join join) {
        this.nestedJoins.add(join);
    }

//    public GTable getFromTable() {
//        return fromTable;
//    }
//
//    public void setFromTable(GTable fromTable) {
//        this.fromTable = fromTable;
//    }

//    public GTable getToTable() {
//        return toTable;
//    }
//
//    public void setToTable(GTable toTable) {
//        this.toTable = toTable;
//    }

    public void setNestedJoins(List<Join> nestedJoins) {
        this.nestedJoins = nestedJoins;
    }

    public SubquerySource getToSubquery() {
        return toSubquery;
    }

    public void setToSubquery(SubquerySource toSubquery) {
        this.toSubquery = toSubquery;
    }
}
