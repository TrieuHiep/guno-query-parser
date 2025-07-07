package vn.guno.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.guno.dto.ApiResponse;
import vn.guno.exception.ReportGenerationException;
import vn.guno.service.PingService;

@RestController
@RequestMapping("/")
@Tag(name = "Ping")
//@Hidden
public class PingController extends BaseController {

    @Autowired
    private PingService pingService;

    @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> pingFunction(
            HttpServletRequest request
    ) {
        String response;
        ApiResponse serverResponse;
        HttpStatus apiStatus = HttpStatus.OK;
        try {
            serverResponse = this.pingService.ping();
            response = gson.toJson(serverResponse, ApiResponse.class);
        } catch (ReportGenerationException se) {
            se.printStackTrace();
            apiStatus = HttpStatus.FORBIDDEN;
            response = buildFailureResponse(apiStatus.value(), se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            apiStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            serverResponse = new ApiResponse.Builder(0, apiStatus.value())
                    .buildMessage("an error occurred when performing ping operation")
                    .build();
            response = gson.toJson(serverResponse, ApiResponse.class);
        }
        return new ResponseEntity<>(response, apiStatus);
    }
}
