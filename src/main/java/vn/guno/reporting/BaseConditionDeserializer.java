package vn.guno.reporting;

import com.google.gson.*;
import vn.guno.reporting.core.BaseCondition;
import vn.guno.reporting.core.Condition;
import vn.guno.reporting.core.LogicalCondition;

import java.lang.reflect.Type;

public class BaseConditionDeserializer implements JsonDeserializer<BaseCondition> {
    @Override
    public BaseCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        if (obj.has("conditions")) {
            return context.deserialize(json, LogicalCondition.class);
        } else if (obj.has("column")) {
            return context.deserialize(json, Condition.class);
        } else {
            throw new JsonParseException("Unknown BaseCondition type: " + json);
        }
    }
}