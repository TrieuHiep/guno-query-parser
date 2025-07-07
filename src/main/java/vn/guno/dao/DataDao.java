package vn.guno.dao;
import org.json.JSONObject;
import java.util.List;

public interface DataDao {
    public abstract List<JSONObject> queryReport(String sqlCommand, List<Object> bindValues);

}
