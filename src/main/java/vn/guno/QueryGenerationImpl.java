package vn.guno;

import org.jooq.*;
import org.jooq.impl.DSL;
import vn.guno.core.Condition;
import vn.guno.core.*;
import vn.guno.global.SortDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.jooq.impl.DSL.*;

public class QueryGenerationImpl {

    private org.jooq.Condition resolveLogicalCondition(BaseCondition cond) {
        if (cond instanceof LogicalCondition logic) {
            return switch (logic.getOperator()) {
                case AND -> and(
                        logic.getConditions().stream()
                                .map(this::resolve)
                                .toList()
                );
                case OR -> or(
                        logic.getConditions().stream()
                                .map(this::resolve)
                                .toList()
                );
                case NOT -> not(
                        logic.getConditions().stream()
                                .findFirst()
                                .map(this::resolve)
                                .orElseThrow(() -> new IllegalArgumentException("NOT condition must contain one condition"))
                );
            };
        }
        throw new IllegalArgumentException("resolveLogicalCondition Unsupported condition type: " + cond.getClass());
    }

    private org.jooq.Condition resolve(Object cond) {
        if (cond instanceof Condition c) {
            Field<Object> field = field(name(c.getColumn().getTable().getAlias(), c.getColumn().getName()));
            Object val = c.getValue();
            return switch (c.getOperator()) {
                case EQUALS -> field.eq(val);
                case NOT_EQUALS -> field.ne(val);
                case NOT_IN -> field.notIn(val);
                case GREATER_THAN -> field.gt(val);
                case LESS_THAN -> field.lt(val);
                case LIKE -> field.like(val.toString());
                case IN -> field.in((Collection<?>) val);
                case BETWEEN -> {
                    List<?> range = (List<?>) val;
                    yield field.between(range.get(0), range.get(1));
                }
            };
        } else if (cond instanceof LogicalCondition logic) {
            return resolveLogicalCondition(logic);
        }
        throw new IllegalArgumentException("Unsupported condition type: " + cond.getClass());
    }

    // PUBLIC: Default method (backward compatible)
    public String buildSQL(ReportQuery reportQuery, DSLContext ctx) {
        return buildSQL(reportQuery, ctx, true); // isTopLevel = true
    }

    private void applyNestedJoins(SelectJoinStep<?> query, Join parentJoin) {
        for (Join nested : parentJoin.getNestedJoins()) {
            Table<?> to = table(name(nested.getToTable().getName())).as(nested.getToTable().getAlias());
//            Field<Object> left = field(name(nested.getOnCondition().getColumn().getTable().getAlias(), nested.getOnCondition().getColumn().getName()));
//            Field<Object> right = field((String) nested.getOnCondition().getValue());

            JoinCondition joinCond = nested.getOnCondition();
            Field<Object> left = field(name(
                    joinCond.getLeftColumn().getTable().getAlias(),
                    joinCond.getLeftColumn().getName()
            ));
            Field<Object> right = field(name(
                    joinCond.getRightColumn().getTable().getAlias(),
                    joinCond.getRightColumn().getName()
            ));

            org.jooq.Condition joinComparisonCond = left.eq(right); // default: join on EQUALS

            switch (joinCond.getOperator()) { // ON A.id = > < B.id
                case EQUALS -> joinComparisonCond = left.eq(right);
                case GREATER_THAN -> joinComparisonCond = left.gt(right);
                case NOT_EQUALS -> joinComparisonCond = left.ne(right);
                case GREATER_EQUAL -> joinComparisonCond = left.ge(right);
                case LESS_EQUAL -> joinComparisonCond = left.le(right);
                case LESS_THAN -> joinComparisonCond = left.lt(right);
            }

            switch (nested.getJoinType()) {
                case INNER -> query.join(to).on(joinComparisonCond);
                case LEFT -> query.leftJoin(to).on(joinComparisonCond);
                case RIGHT -> query.rightJoin(to).on(joinComparisonCond);
                case FULL -> query.fullOuterJoin(to).on(joinComparisonCond);
            }
            applyNestedJoins(query, nested);
        }
    }

    public String buildSQL(ReportQuery reportQuery, DSLContext ctx, boolean isTopLevel) {
        GTable fromTable = reportQuery.getFromTable();
        List<Dimension> dimensions = reportQuery.getDimensions();
        List<Metric> metrics = reportQuery.getMetrics();

        BaseCondition whereCondition = reportQuery.getWhereCondition();
        BaseCondition havingCondition = reportQuery.getHavingCondition();

        List<Join> joins = reportQuery.getJoins();
        Pagination pagination = reportQuery.getPagination();
        List<OrderBy> orderBy = reportQuery.getOrderBy();
        List<DerivedMetric> derivedMetrics = reportQuery.getDerivedMetrics();

        TableLike<?> from;

        if (fromTable.isSubquery()) {
            ReportQuery subReportQuery = fromTable.getSubquery().getQuery();
            String subquerySQL = new QueryGenerationImpl().buildSQL(subReportQuery, ctx, false);
            from = DSL.table("(" + subquerySQL + ")").as(fromTable.getSubquery().getAlias());
        } else {
            from = DSL.table(DSL.name(fromTable.getName())).as(fromTable.getAlias());
        }

        List<SelectFieldOrAsterisk> selectFields = new ArrayList<>();
        for (Dimension dim : dimensions) {
            Column col = dim.getColumn();
            selectFields.add(field(name(col.getTable().getAlias(), col.getName())));
        }

        for (Metric metric : metrics) {
            Field field = field(name(metric.getColumn().getTable().getAlias(), metric.getColumn().getName()));
            switch (metric.getType()) {
                case SUM -> selectFields.add(sum(field).as(metric.getAlias()));
                case AVG -> selectFields.add(avg(field).as(metric.getAlias()));
                case MAX -> selectFields.add(max(field).as(metric.getAlias()));
                case MIN -> selectFields.add(min(field).as(metric.getAlias()));
                case COUNT -> selectFields.add(count(field).as(metric.getAlias()));
                case COUNT_DISTINCT -> selectFields.add(countDistinct(field).as(metric.getAlias()));
            }
        }

        for (DerivedMetric dm : derivedMetrics) {
            selectFields.add(field(dm.getExpression()).as(dm.getAlias()));
        }

        // check if DISTINCT is used
        SelectSelectStep<?> selectStep;
        if (reportQuery.isDistinct()) {
            selectStep = ctx.selectDistinct(selectFields);
        } else {
            selectStep = ctx.select(selectFields);
        }

        SelectJoinStep<?> selectJoinStep = selectStep.from(from);

        // Apply joins
        for (Join join : joins) {
//            Table<?> to = table(name(join.getToTable().getName())).as(join.getToTable().getAlias());

            Table<?> to;

            if (join.getToSubquery() != null) {
                ReportQuery sub = join.getToSubquery().getQuery();
                String subquerySQL = buildSQL(sub, ctx, false);
                to = DSL.table("(" + subquerySQL + ")").as(join.getToSubquery().getAlias());
            } else {
                to = DSL.table(DSL.name(join.getToTable().getName())).as(join.getToTable().getAlias());
            }

            JoinCondition joinCond = join.getOnCondition();
            Field<Object> left = field(name(
                    joinCond.getLeftColumn().getTable().getAlias(),
                    joinCond.getLeftColumn().getName()
            ));
            Field<Object> right = field(name(
                    joinCond.getRightColumn().getTable().getAlias(),
                    joinCond.getRightColumn().getName()
            ));

            org.jooq.Condition joinComparisonCond = left.eq(right);

            switch (joinCond.getOperator()) { // ON A.id = > < B.id
                case EQUALS -> joinComparisonCond = left.eq(right);
                case GREATER_THAN -> joinComparisonCond = left.gt(right);
                case NOT_EQUALS -> joinComparisonCond = left.ne(right);
                case GREATER_EQUAL -> joinComparisonCond = left.ge(right);
                case LESS_EQUAL -> joinComparisonCond = left.le(right);
                case LESS_THAN -> joinComparisonCond = left.lt(right);
                default ->
                        throw new IllegalStateException("Unexpected join Comparison value: " + joinCond.getOperator());
            }

            switch (join.getJoinType()) { // table A inner / left / right / full JOIN tbl B
                case INNER -> selectJoinStep = selectJoinStep.join(to).on(joinComparisonCond);
                case LEFT -> selectJoinStep = selectJoinStep.leftJoin(to).on(joinComparisonCond);
                case RIGHT -> selectJoinStep = selectJoinStep.rightJoin(to).on(joinComparisonCond);
                case FULL -> selectJoinStep = selectJoinStep.fullOuterJoin(to).on(joinComparisonCond);
            }

            applyNestedJoins(selectJoinStep, join);

        }

        // Where and Having
        org.jooq.Condition where = (whereCondition != null) ? resolveLogicalCondition(whereCondition) : DSL.noCondition();
        org.jooq.Condition having = (havingCondition != null) ? resolveLogicalCondition(havingCondition) : null;

        SelectConditionStep<?> query = selectJoinStep.where(where);

        List<Field<?>> groupFields = new ArrayList<>();
        if (!dimensions.isEmpty()) {
            for (Dimension dim : dimensions) {
                Column col = dim.getColumn();
                groupFields.add(field(name(col.getTable().getAlias(), col.getName())));
            }
        }

        List<OrderField<?>> orderFields = new ArrayList<>();
        for (OrderBy ob : orderBy) {
            Field<?> field = field(name(ob.getColumn().getTable().getAlias(), ob.getColumn().getName()));
            if (ob.getDirection() == SortDirection.ASC) {
                orderFields.add(field.asc());
            } else {
                orderFields.add(field.desc());
            }
        }

//        int limit = 100, offset = 0;
//        if (pagination != null) {
//            limit = pagination.getLimit();
//            offset = pagination.getOffset();
//        }


        SelectHavingStep<?> groupStep = query.groupBy(groupFields);
        SelectForUpdateStep<?> queryBuilder;


        if (isTopLevel) { // Top-level query with pagination
            int limit, offset;
            if (pagination != null) {
                limit = pagination.getLimit();
                offset = pagination.getOffset();
            } else {
                limit = 100; // default value
                offset = 0;
            }


            if (having != null) {
                queryBuilder = groupStep
                        .having(having)
                        .orderBy(orderFields)
                        .limit(limit)
                        .offset(offset);
            } else {
                queryBuilder = groupStep
                        .orderBy(orderFields)
                        .limit(limit)
                        .offset(offset);
            }

        } else { // Subquery - NO LIMIT/OFFSET
            if (having != null) {
                queryBuilder = groupStep
                        .having(having)
                        .orderBy(orderFields);
            } else {
                queryBuilder = groupStep
                        .orderBy(orderFields);
            }
        }

        String sql = queryBuilder.getSQL();
        List<Object> bindValues = queryBuilder.getBindValues();

        System.out.println(sql);
        System.out.println("binding values: " + bindValues);
        return sql;
    }
}
