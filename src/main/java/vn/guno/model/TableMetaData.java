package vn.guno.model;

import java.util.List;

public class TableMetaData {
    private String schema;
    private String tableName;
    private List<ColumnMetaData> columnMetaDataList;

    public TableMetaData() {
    }

    public TableMetaData(String tableName, List<ColumnMetaData> columnMetaDataList) {
        this.tableName = tableName;
        this.columnMetaDataList = columnMetaDataList;
    }

    public TableMetaData(String schema, String tableName, List<ColumnMetaData> columnMetaDataList) {
        this.schema = schema;
        this.tableName = tableName;
        this.columnMetaDataList = columnMetaDataList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnMetaData> getColumnMetaDataList() {
        return columnMetaDataList;
    }

    public void setColumnMetaDataList(List<ColumnMetaData> columnMetaDataList) {
        this.columnMetaDataList = columnMetaDataList;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "TableMetaData{" +
                "tableName='" + tableName + '\'' +
                ", columnMetaDataList=" + columnMetaDataList +
                '}';
    }
}
