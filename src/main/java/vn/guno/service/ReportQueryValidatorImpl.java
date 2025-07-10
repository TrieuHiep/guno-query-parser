package vn.guno.service;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import vn.guno.exception.ReportGenerationException;
import vn.guno.reporting.ReportQuery;

import java.util.ArrayList;

import static vn.guno.global.ErrorCode.INVALID_FIELD;
import static vn.guno.global.ErrorCode.INVALID_INPUT;

@Component
public class ReportQueryValidatorImpl implements ReportQueryValidator {

    @Autowired
    @Qualifier("gsonCustomDeserializer")
    private Gson gson;

    @Override
    public ReportQuery parseQuery(String jsonInput) throws Exception {
        if (jsonInput == null || jsonInput.trim().isEmpty()) {
            throw new ReportGenerationException(
                    "JSON input cannot be null or empty",
                    INVALID_INPUT);
        }

        try {
            ReportQuery query = gson.fromJson(jsonInput, ReportQuery.class);

            // Basic null check after parsing
            if (query == null) {
                throw new ReportGenerationException(
                        "Failed to parse JSON - resulted in null object",
                        INVALID_INPUT
                );
            }

            // Basic structure validation
            validateBasicStructure(query);

            return query;

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw new ReportGenerationException(
                    "Invalid JSON syntax in ReportQuery",
                    INVALID_INPUT
            );
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new ReportGenerationException(
                    "Failed to parse ReportQuery from JSON",
                    INVALID_INPUT
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ReportGenerationException(
                    "Unexpected error while parsing ReportQuery",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage()
            );
        }
    }

    private void validateBasicStructure(ReportQuery query) throws ReportGenerationException {
        // Check if fromTable exists
        if (query.getFromTable() == null) throw new ReportGenerationException(
                "ReportQuery must have a 'fromGTable' field",
                INVALID_FIELD
        );

        // Check if fromTable has required fields
        if (isBlank(query.getFromTable().getAlias())) throw new ReportGenerationException(
                "fromGTable must have an 'alias' field",
                INVALID_FIELD
        );


        // For non-subquery tables, name is required
        if (!query.getFromTable().isSubquery() &&
                isBlank(query.getFromTable().getName()))
            throw new ReportGenerationException(
                    "fromGTable must have a 'name' field when not using subquery",
                    INVALID_FIELD
            );


        // Check list fields are not null (Gson might not initialize them)
        if (query.getDimensions() == null) {
            query.setDimensions(new ArrayList<>());
        }
        if (query.getMetrics() == null) {
            query.setMetrics(new ArrayList<>());
        }
        if (query.getJoins() == null) {
            query.setJoins(new ArrayList<>());
        }
        if (query.getOrderBy() == null) {
            query.setOrderBy(new ArrayList<>());
        }
        if (query.getDerivedMetrics() == null) {
            query.setDerivedMetrics(new ArrayList<>());
        }
        if (query.getGroupBy() == null) {
            query.setGroupBy(new ArrayList<>());
        }
    }

    private boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

}
