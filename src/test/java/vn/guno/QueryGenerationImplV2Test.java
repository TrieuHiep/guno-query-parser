package vn.guno;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.jsqlparser.JSQLParserException;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import vn.guno.reporting.BaseConditionDeserializer;
import vn.guno.reporting.QueryGenerationImpl;
import vn.guno.reporting.QueryResult;
import vn.guno.reporting.ReportQuery;
import vn.guno.reporting.core.BaseCondition;
import vn.guno.utils.SQLComparatorUtils;
import vn.guno.utils.TestDataLoader;

public class QueryGenerationImplV2Test {

    Gson gson;
    private String rawJson;

    private String expectedSQL;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(BaseCondition.class, new BaseConditionDeserializer())
                .create();
    }

    @Test
    public void complexJoinQuery() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/GeneralComplexJoinQuery.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/GeneralComplexJoinQuery.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @After
    public void tearDown() {
//        ReportQuery query = gson.fromJson(rawSQL, ReportQuery.class);
//        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
//        System.out.println("final binding: " + queryResult.getBindValues());
//        System.out.println("--------------------------");
    }
}