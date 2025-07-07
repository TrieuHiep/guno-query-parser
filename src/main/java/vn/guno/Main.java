package vn.guno;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import vn.guno.core.BaseCondition;

public class Main {
    public static void main(String[] args) {
        String rawJson = """
                {
                  "fromGTable": {
                    "name": "orders",
                    "alias": "o"
                  },
                  "dimensions": [
                    {
                      "column": {
                        "table": { "alias": "o" },
                        "name": "status"
                      }
                    }
                  ],
                  "metrics": [
                    {
                      "type": "COUNT",
                      "alias": "total_orders",
                      "column": {
                        "table": { "alias": "o" },
                        "name": "id"
                      }
                    }
                  ],
                  "whereCondition": {
                    "operator": "AND",
                    "conditions": [
                      {
                        "column": {
                          "table": { "alias": "o" },
                          "name": "country"
                        },
                        "operator": "EQUALS",
                        "value": "US"
                      },
                      {
                        "operator": "OR",
                        "conditions": [
                          {
                            "column": {
                              "table": { "alias": "o" },
                              "name": "status"
                            },
                            "operator": "EQUALS",
                            "value": "PENDING"
                          },
                          {
                            "column": {
                              "table": { "alias": "o" },
                              "name": "total"
                            },
                            "operator": "GREATER_THAN",
                            "value": 100
                          }
                        ]
                      },
                      {
                        "operator": "NOT",
                        "conditions": [
                          {
                            "column": {
                              "table": { "alias": "o" },
                              "name": "payment_method"
                            },
                            "operator": "EQUALS",
                            "value": "CASH"
                          }
                        ]
                      }
                    ]
                  },
                  "orderBy": [
                    {
                      "column": {
                        "table": { "alias": "o" },
                        "name": "status"
                      },
                      "direction": "ASC"
                    }
                  ],
                  "pagination": {
                    "limit": 10,
                    "offset": 0
                  }
                }
                                
                """;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BaseCondition.class, new BaseConditionDeserializer())
                .create();
        ReportQuery query = gson.fromJson(rawJson, ReportQuery.class);
        new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.POSTGRES));
    }
}
