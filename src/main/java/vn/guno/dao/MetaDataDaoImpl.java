package vn.guno.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.guno.model.ColumnMetaData;
import vn.guno.model.SchemaMetaData;
import vn.guno.model.TableMetaData;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MetaDataDaoImpl implements MetaDataDao {
    protected static final Logger eLogger = LogManager.getLogger("ErrorLog");

    @Autowired
    private DataSource dataSource;

    @Override
    public SchemaMetaData getSchemaMetaData(String schemaName) {
        SchemaMetaData schemaMetaData = null;
        try (Connection conn = dataSource.getConnection()) { // auto close connection when use try-with-resource
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet tables = meta.getTables(null, schemaName, "%", new String[]{"TABLE"});
            schemaMetaData = new SchemaMetaData();
            List<String> tableList = new ArrayList<>();
            System.out.println("Tables in schema '" + schemaName + "':");
            schemaMetaData.setSchemaName(schemaName);

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                tableList.add(tableName);
                System.out.println(" - " + tableName);
            }
            schemaMetaData.setTableName(tableList);
        } catch (SQLException e) {
            eLogger.error("failed to get schema metadata, reason: {}", e.getMessage());
            e.printStackTrace();
        }
        return schemaMetaData;
    }

    @Override
    public TableMetaData getTableMetaData(String schemaName, String tableName) {
        TableMetaData tableMetaData = null;

        try (Connection conn = dataSource.getConnection()) { // auto close connection when use try-with-resource
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet columns = meta.getColumns(null, schemaName, tableName, "%");

            System.out.println("Columns in table '" + tableName + "':");
            tableMetaData = new TableMetaData();
            tableMetaData.setTableName(tableName);
            tableMetaData.setSchema(schemaName);
            List<ColumnMetaData> columnMetaDataList = new ArrayList<>();

            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String dataType = columns.getString("TYPE_NAME");
                int size = columns.getInt("COLUMN_SIZE");
                columnMetaDataList.add(new ColumnMetaData(columnName, dataType));
                System.out.println(" - " + columnName + " (" + dataType + "(" + size + "))");
            }
            tableMetaData.setColumnMetaDataList(columnMetaDataList);

        } catch (SQLException e) {
            eLogger.error("failed to get metadata for table {}, reason: {}", tableName, e.getMessage());
            e.printStackTrace();
        }
        return tableMetaData;
    }
}
