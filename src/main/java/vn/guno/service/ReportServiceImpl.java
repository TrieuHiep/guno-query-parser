package vn.guno.service;

import com.google.gson.Gson;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.guno.reporting.QueryGenerationImpl;
import vn.guno.reporting.ReportQuery;
import vn.guno.dao.DataDao;
import vn.guno.dto.ApiResponse;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

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
        String sqlCommand = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
//        return genReport(input, tableName);

        return new ApiResponse.Builder(1, HttpStatus.OK.value())
                .build();
    }

//    private ApiResponse genReport(ReportRequest input, String tableName) throws Exception {
//
//
//        String sql = queryBuilder.getSQL();
//        System.out.println(sql);
//        List<Object> bindValues = queryBuilder.getBindValues();
//        List<JSONObject> rs = this.dataDao.queryReport(sql, bindValues);
//
//        List<JsonObject> rsObj = rs.stream()
//                .map(e -> gson.fromJson(e.toString(), JsonObject.class))
//                .toList();
//
//        return new ApiResponse.Builder(1, HttpStatus.OK.value())
//                .buildData(rsObj)
//                .build();
//    }
}
