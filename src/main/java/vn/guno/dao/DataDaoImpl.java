package vn.guno.dao;

import com.ecyrd.speed4j.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DataDaoImpl implements DataDao {

    protected static final Logger eLogger = LogManager.getLogger("ErrorLog");

    private static final Logger dbLogger = LogManager.getLogger("DatabaseLog");


    @Autowired
    private DataSource dataSource;

    @Override
    public List<JSONObject> queryReport(String sqlCommand, List<Object> bindValues) {
        List<JSONObject> jsonObjects = new ArrayList<>();
        Connection conn = null;

        try {

            conn = dataSource.getConnection();
//            String setSessionSQL = "set SESSION tidb_isolation_read_engines = \"tiflash\"";
//            conn.prepareStatement(setSessionSQL).execute();
            PreparedStatement statement;

            statement = conn.prepareStatement(sqlCommand);
            for (int i = 0; i < bindValues.size(); i++) {
                statement.setObject(i + 1, bindValues.get(i));
            }

            StopWatch sw = new StopWatch();

            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();

            // Retrieving the list of column names
            int count = metaData.getColumnCount();

            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= count; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object resultValue = resultSet.getObject(columnName);
                    jsonObject.put(columnName, resultValue);
                }
                jsonObjects.add(jsonObject);
            }
            dbLogger.info("sql command: {}, processing time: {}", sqlCommand, sw.stop());
        } catch (Exception e) {
            eLogger.error("failed to generate report, reason: {}", e.getMessage());
            e.printStackTrace();
        } finally {
            releaseConn(conn);
        }
        return jsonObjects;
    }

    private void releaseConn(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
