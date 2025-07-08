package vn.guno.model;

import java.util.List;

public class SchemaMetaData {
    private String schemaName;
    private List<String> tableName;

    public SchemaMetaData() {
    }

    public SchemaMetaData(String schemaName, List<String> tableName) {
        this.schemaName = schemaName;
        this.tableName = tableName;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public List<String> getTableName() {
        return tableName;
    }

    public void setTableName(List<String> tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return "SchemaMetaData{" +
                "schemaName='" + schemaName + '\'' +
                ", tableName=" + tableName +
                '}';
    }
}
