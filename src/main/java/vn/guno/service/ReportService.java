package vn.guno.service;


import vn.guno.dto.ApiResponse;

public interface ReportService {
    public abstract ApiResponse generateReport(String jsonBody) throws Exception;

}
