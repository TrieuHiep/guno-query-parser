package vn.guno.service;


import vn.guno.dto.ApiResponse;

public interface PingService {
    public abstract ApiResponse ping() throws Exception;
}
