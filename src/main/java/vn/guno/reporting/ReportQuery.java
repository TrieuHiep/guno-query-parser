package vn.guno.reporting;

import vn.guno.reporting.core.*;

import java.util.ArrayList;
import java.util.List;

public class ReportQuery {
    private boolean distinct;
    private GTable fromGTable;
    private List<Join> joins = new ArrayList<>();
    private List<Dimension> dimensions = new ArrayList<>();
    private List<Metric> metrics = new ArrayList<>();
    private List<DerivedMetric> derivedMetrics = new ArrayList<>();
    private BaseCondition whereCondition;
    private BaseCondition havingCondition;
    private List<OrderBy> orderBy = new ArrayList<>();
    private Pagination pagination;

    //  Explicit GROUP BY control
    private List<Dimension> groupBy = new ArrayList<>();  // User-specified grouping

    public GTable getFromTable() {
        return fromGTable;
    }

    public void setFromTable(GTable fromGTable) {
        this.fromGTable = fromGTable;
    }

    public List<Join> getJoins() {
        return joins;
    }

    public void setJoins(List<Join> joins) {
        this.joins = joins;
    }

    public List<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<Dimension> dimensions) {
        this.dimensions = dimensions;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public void setDerivedMetrics(List<DerivedMetric> derivedMetrics) {
        this.derivedMetrics = derivedMetrics;
    }

    public List<DerivedMetric> getDerivedMetrics() {
        return derivedMetrics;
    }

    public BaseCondition getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(BaseCondition whereCondition) {
        this.whereCondition = whereCondition;
    }

    public BaseCondition getHavingCondition() {
        return havingCondition;
    }

    public void setHavingCondition(BaseCondition havingCondition) {
        this.havingCondition = havingCondition;
    }

    public List<OrderBy> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<OrderBy> orderBy) {
        this.orderBy = orderBy;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public GTable getFromGTable() {
        return fromGTable;
    }

    public void setFromGTable(GTable fromGTable) {
        this.fromGTable = fromGTable;
    }

    public List<Dimension> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(List<Dimension> groupBy) {
        this.groupBy = groupBy;
    }
}
