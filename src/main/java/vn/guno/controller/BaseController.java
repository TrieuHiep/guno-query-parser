package vn.guno.controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import vn.guno.dto.ApiResponse;

import java.util.Map;

/**
 * @author tatsuya
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
public abstract class BaseController {
    protected static final Logger requestLogger = LogManager.getLogger("RequestLog");
    protected static final Logger eLogger = LogManager.getLogger("ErrorLog");
    protected static final Logger cLogger = LogManager.getLogger("CacheLog");
    protected static final Logger pLogger = LogManager.getLogger("WorkerLog");

    @Autowired
    @Qualifier("gsonCustom")
    protected Gson gson;


    public BaseController() {
    }

    protected String getRequestParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        request.getParameterMap().keySet().stream().sorted().forEach(k -> {
            String v = request.getParameter(k);
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(k).append("=").append(v);
        });
        return sb.toString();
    }

    protected String getPayloadParams(Map<String, String> payLoad) {
        StringBuilder sb = new StringBuilder();
        payLoad.keySet().stream().sorted().forEach(k -> {
            String v = payLoad.get(k);
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(k).append("=").append(v);
        });
        return sb.toString();
    }

    protected String buildFailureResponse(int code, String message) {
        ApiResponse failureResponse = new ApiResponse.Builder(0, code)
                .buildMessage(message)
                .build();
        return gson.toJson(failureResponse);
    }

    protected String buildFailureResponse(int code, String message, Object data) {
        ApiResponse failureResponse = new ApiResponse.Builder(0, code)
                .buildMessage(message)
                .buildData(data)
                .build();
        return gson.toJson(failureResponse);
    }

    protected long parseNumber(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}
