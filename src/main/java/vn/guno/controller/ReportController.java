package vn.guno.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.guno.dto.ApiResponse;
import vn.guno.exception.ReportGenerationException;
import vn.guno.service.ReportService;

/**
 * @author tatsuya
 * @created 05/06/2024
 */
@RestController
@RequestMapping("/report-mgmt")
@Tag(name = "Report Management APIs")
public class ReportController extends BaseController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> generateReport(
            HttpServletRequest request,
            @RequestBody String jsonBody
    ) {
        String response;
        ApiResponse serverResponse;
        HttpStatus apiStatus = HttpStatus.OK;
        try {
            serverResponse = this.reportService.generateReport(jsonBody);
            response = gson.toJson(serverResponse, ApiResponse.class);
        } catch (ReportGenerationException se) {
            se.printStackTrace();
            response = buildFailureResponse(se.getCode(), se.getMessage(), se.getRootCause());
        } catch (Exception e) {
            e.printStackTrace();
            apiStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            serverResponse = new ApiResponse.Builder(0, apiStatus.value())
                    .buildMessage("an error occurred when generating report")
                    .build();
            response = gson.toJson(serverResponse, ApiResponse.class);
        }
        return new ResponseEntity<>(response, apiStatus);
    }

}