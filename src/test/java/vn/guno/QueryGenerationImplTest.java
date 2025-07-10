package vn.guno;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.jpa.repository.query.JSqlParserUtils;
import vn.guno.reporting.BaseConditionDeserializer;
import vn.guno.reporting.QueryGenerationImpl;
import vn.guno.reporting.QueryResult;
import vn.guno.reporting.ReportQuery;
import vn.guno.reporting.core.BaseCondition;
import vn.guno.utils.SQLComparatorUtils;
import vn.guno.utils.TestDataLoader;

public class QueryGenerationImplTest {

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
    public void testSimpleSelectOnly() throws JSQLParserException {
        // Just dimensions, no metrics, no joins
        rawJson = TestDataLoader.loadTextFromTestCases("input/SimpleSelectOnly.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/SimpleSelectOnly.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void simpleSelect() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/SimpleSelect.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/SimpleSelect.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void whereWithAndOrNot() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/WhereWithAndOrNot.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/WhereWithAndOrNot.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void joinBetweenTables() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/JoinBetweenTables.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/JoinBetweenTables.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void havingWithFilters() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/HavingWithFilters.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/HavingWithFilters.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void deliveredMetrics() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/DeliveredMetrics.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/DeliveredMetrics.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void distinctAndCount() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/DistinctAndCount.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/DistinctAndCount.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void subQueryInFrom() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/SubQueryInFrom.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/SubQueryInFrom.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void subQueryInJoin() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/SubQueryInJoin.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/SubQueryInJoin.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void nestedJoinWithSubQuery() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/NestedJoinWithSubQuery.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/NestedJoinWithSubQuery.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void deepLevelBinding() throws JSQLParserException {
        // Just dimensions, no metrics, no joins
        rawJson = TestDataLoader.loadTextFromTestCases("input/DeepLevelBinding.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/DeepLevelBinding.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));

    }

    @Test
    public void complexJoinQuery() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/ComplexJoinQuery.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/ComplexJoinQuery.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void testInAndBetweenOperators() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/InAndBetweenOperators.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/InAndBetweenOperators.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void testLikeOperator() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/LikeOperator.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/LikeOperator.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void testEmptySelectFields() throws JSQLParserException {
        // Should handle gracefully - what happens with no SELECT fields?

        rawJson = TestDataLoader.loadTextFromTestCases("input/EmptySelectFields.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/EmptySelectFields.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @Test
    public void testComplexBindingOrder() throws JSQLParserException {
        rawJson = TestDataLoader.loadTextFromTestCases("input/ComplexBindingOrder.json");
        expectedSQL = TestDataLoader.loadTextFromTestCases("output/ComplexBindingOrder.sql");
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
        Assert.assertTrue(SQLComparatorUtils.sqlEquals(queryResult.getSql(), expectedSQL));
    }

    @After
    public void tearDown() throws JSQLParserException {
//        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
//        QueryResult queryResult = new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
//        Statement statement = CCJSqlParserUtil.parse(queryResult.getSql());
//        System.out.println(statement.toString());
//        System.out.println("final binding: " + queryResult.getBindValues());
//        System.out.println("--------------------------");
    }
}