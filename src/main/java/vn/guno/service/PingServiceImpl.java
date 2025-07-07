package vn.guno.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.guno.dto.ApiResponse;

@Service
public class PingServiceImpl implements PingService {
    @Override
    public ApiResponse ping() throws Exception {
        return new ApiResponse.Builder(1, HttpStatus.OK.value())
                .buildMessage("hello")
                .build();
    }
}
