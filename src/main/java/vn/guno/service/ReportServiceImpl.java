package vn.guno.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.guno.dao.DataDao;
import vn.guno.dto.ApiResponse;
import vn.guno.reporting.QueryGenerationImpl;
import vn.guno.reporting.QueryResult;
import vn.guno.reporting.ReportQuery;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private DataDao dataDao;

    @Autowired
    @Qualifier("gsonCustomDeserializer")
    private Gson gson;

    @Override
    public ApiResponse generateReport(String jsonBody) throws Exception {
        ReportQuery query = gson.fromJson(jsonBody, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        String sql = queryResult.getSql();
        List<Object> bindValues = queryResult.getBindValues();
        List<JSONObject> rs = this.dataDao.queryReport(sql, bindValues);
        List<JsonObject> rsObj = rs.stream()
                .map(e -> gson.fromJson(e.toString(), JsonObject.class))
                .toList();

        return new ApiResponse.Builder(1, HttpStatus.OK.value())
                .buildData(rsObj)
                .build();
    }

}
