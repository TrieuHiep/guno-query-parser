package vn.guno.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QueryGeneratorDto {
    @Expose
    @SerializedName("debug")
    private String sqlQueryDebug;

    @Expose
    @SerializedName("result")
    private Object data;

    public QueryGeneratorDto() {
    }

    public QueryGeneratorDto(String sqlQueryDebug, Object data) {
        this.sqlQueryDebug = sqlQueryDebug;
        this.data = data;
    }

    public String getSqlQueryDebug() {
        return sqlQueryDebug;
    }

    public void setSqlQueryDebug(String sqlQueryDebug) {
        this.sqlQueryDebug = sqlQueryDebug;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
