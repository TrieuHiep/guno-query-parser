package vn.guno.dao;

import vn.guno.model.SchemaMetaData;
import vn.guno.model.TableMetaData;

public interface MetaDataDao {
    public abstract SchemaMetaData getSchemaMetaData(String schemaName);

    public abstract TableMetaData getTableMetaData(String schemaName, String tableName);

}
