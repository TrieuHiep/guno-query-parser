package vn.guno.service;

import vn.guno.reporting.ReportQuery;

public interface ReportQueryValidator {
    public abstract ReportQuery parseQuery(String json) throws Exception;
}
