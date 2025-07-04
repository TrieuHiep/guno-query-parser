package vn.guno;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vn.guno.core.BaseCondition;

public class QueryGenerationImplV2Test {

    Gson gson;
    private String rawSQL;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(BaseCondition.class, new BaseConditionDeserializer())
                .create();
    }

    @Test
    public void complexJoinQuery() {
        rawSQL = """
                {
                    "fromGTable": {
                        "name": "orders",
                        "alias": "o"
                    },
                    "dimensions": [
                        {
                            "column": {
                                "table": {
                                    "alias": "o"
                                },
                                "name": "order_number"
                            }
                        },
                        {
                            "column": {
                                "table": {
                                    "alias": "c"
                                },
                                "name": "customer_name"
                            }
                        },
                        {
                            "column": {
                                "table": {
                                    "alias": "a"
                                },
                                "name": "street_address"
                            }
                        },
                        {
                            "column": {
                                "table": {
                                    "alias": "co"
                                },
                                "name": "country_name"
                            }
                        },
                        {
                            "column": {
                                "table": {
                                    "alias": "customer_stats"
                                },
                                "name": "total_orders"
                            }
                        },
                        {
                            "column": {
                                "table": {
                                    "alias": "customer_stats"
                                },
                                "name": "lifetime_value"
                            }
                        }
                    ],
                    "metrics": [
                        {
                            "type": "SUM",
                            "alias": "total_order_value",
                            "column": {
                                "table": {
                                    "alias": "oi"
                                },
                                "name": "total_price"
                            }
                        },
                        {
                            "type": "COUNT",
                            "alias": "total_items",
                            "column": {
                                "table": {
                                    "alias": "oi"
                                },
                                "name": "id"
                            }
                        },
                        {
                            "type": "AVG",
                            "alias": "avg_item_price",
                            "column": {
                                "table": {
                                    "alias": "p"
                                },
                                "name": "price"
                            }
                        },
                        {
                            "type": "MAX",
                            "alias": "max_product_price",
                            "column": {
                                "table": {
                                    "alias": "p"
                                },
                                "name": "price"
                            }
                        },
                        {
                            "type": "COUNT_DISTINCT",
                            "alias": "unique_products",
                            "column": {
                                "table": {
                                    "alias": "p"
                                },
                                "name": "id"
                            }
                        }
                    ],
                    "derivedMetrics": [
                        {
                            "alias": "avg_order_value_per_item",
                            "expression": "SUM(oi.total_price) / COUNT(oi.id)"
                        },
                        {
                            "alias": "price_variance",
                            "expression": "MAX(p.price) - MIN(p.price)"
                        }
                    ],
                    "joins": [
                        {
                            "joinType": "INNER",
                            "toTable": {
                                "name": "customers",
                                "alias": "c"
                            },
                            "onCondition": {
                                "leftColumn": {
                                    "table": {
                                        "alias": "o"
                                    },
                                    "name": "customer_id"
                                },
                                "rightColumn": {
                                    "table": {
                                        "alias": "c"
                                    },
                                    "name": "id"
                                },
                                "operator": "EQUALS"
                            },
                            "nestedJoins": [
                                {
                                    "joinType": "LEFT",
                                    "toTable": {
                                        "name": "addresses",
                                        "alias": "a"
                                    },
                                    "onCondition": {
                                        "leftColumn": {
                                            "table": {
                                                "alias": "c"
                                            },
                                            "name": "shipping_address_id"
                                        },
                                        "rightColumn": {
                                            "table": {
                                                "alias": "a"
                                            },
                                            "name": "id"
                                        },
                                        "operator": "EQUALS"
                                    },
                                    "nestedJoins": [
                                        {
                                            "joinType": "INNER",
                                            "toTable": {
                                                "name": "countries",
                                                "alias": "co"
                                            },
                                            "onCondition": {
                                                "leftColumn": {
                                                    "table": {
                                                        "alias": "a"
                                                    },
                                                    "name": "country_id"
                                                },
                                                "rightColumn": {
                                                    "table": {
                                                        "alias": "co"
                                                    },
                                                    "name": "id"
                                                },
                                                "operator": "EQUALS"
                                            }
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            "joinType": "INNER",
                            "toTable": {
                                "name": "order_items",
                                "alias": "oi"
                            },
                            "onCondition": {
                                "leftColumn": {
                                    "table": {
                                        "alias": "o"
                                    },
                                    "name": "id"
                                },
                                "rightColumn": {
                                    "table": {
                                        "alias": "oi"
                                    },
                                    "name": "order_id"
                                },
                                "operator": "EQUALS"
                            },
                            "nestedJoins": [
                                {
                                    "joinType": "INNER",
                                    "toTable": {
                                        "name": "products",
                                        "alias": "p"
                                    },
                                    "onCondition": {
                                        "leftColumn": {
                                            "table": {
                                                "alias": "oi"
                                            },
                                            "name": "product_id"
                                        },
                                        "rightColumn": {
                                            "table": {
                                                "alias": "p"
                                            },
                                            "name": "id"
                                        },
                                        "operator": "EQUALS"
                                    }
                                }
                            ]
                        },
                        {
                            "joinType": "INNER",
                            "toSubquery": {
                                "alias": "customer_stats",
                                "query": {
                                    "fromGTable": {
                                        "name": "orders",
                                        "alias": "sub_o"
                                    },
                                    "dimensions": [
                                        {
                                            "column": {
                                                "table": {
                                                    "alias": "sub_o"
                                                },
                                                "name": "customer_id"
                                            }
                                        }
                                    ],
                                    "metrics": [
                                        {
                                            "type": "COUNT",
                                            "alias": "total_orders",
                                            "column": {
                                                "table": {
                                                    "alias": "sub_o"
                                                },
                                                "name": "id"
                                            }
                                        },
                                        {
                                            "type": "SUM",
                                            "alias": "lifetime_value",
                                            "column": {
                                                "table": {
                                                    "alias": "sub_o"
                                                },
                                                "name": "total_amount"
                                            }
                                        },
                                        {
                                            "type": "AVG",
                                            "alias": "avg_order_amount",
                                            "column": {
                                                "table": {
                                                    "alias": "sub_o"
                                                },
                                                "name": "total_amount"
                                            }
                                        }
                                    ],
                                    "whereCondition": {
                                        "operator": "AND",
                                        "conditions": [
                                            {
                                                "column": {
                                                    "table": {
                                                        "alias": "sub_o"
                                                    },
                                                    "name": "status"
                                                },
                                                "operator": "EQUALS",
                                                "value": "completed"
                                            }
                                        ]
                                    }
                                }
                            },
                            "onCondition": {
                                "leftColumn": {
                                    "table": {
                                        "alias": "c"
                                    },
                                    "name": "id"
                                },
                                "rightColumn": {
                                    "table": {
                                        "alias": "customer_stats"
                                    },
                                    "name": "customer_id"
                                },
                                "operator": "EQUALS"
                            }
                        }
                    ],
                    "whereCondition": {
                        "operator": "AND",
                        "conditions": [
                            {
                                "column": {
                                    "table": {
                                        "alias": "o"
                                    },
                                    "name": "status"
                                },
                                "operator": "EQUALS",
                                "value": "completed"
                            },
                            {
                                "column": {
                                    "table": {
                                        "alias": "o"
                                    },
                                    "name": "order_date"
                                },
                                "operator": "GREATER_THAN",
                                "value": "2024-01-01"
                            },
                            {
                                "operator": "OR",
                                "conditions": [
                                    {
                                        "column": {
                                            "table": {
                                                "alias": "co"
                                            },
                                            "name": "country_name"
                                        },
                                        "operator": "EQUALS",
                                        "value": "USA"
                                    },
                                    {
                                        "column": {
                                            "table": {
                                                "alias": "co"
                                            },
                                            "name": "country_name"
                                        },
                                        "operator": "EQUALS",
                                        "value": "Canada"
                                    }
                                ]
                            },
                            {
                                "column": {
                                    "table": {
                                        "alias": "customer_stats"
                                    },
                                    "name": "lifetime_value"
                                },
                                "operator": "GREATER_THAN",
                                "value": 1000
                            },
                            {
                                "column": {
                                    "table": {
                                        "alias": "customer_stats"
                                    },
                                    "name": "total_orders"
                                },
                                "operator": "GREATER_EQUAL",
                                "value": 3
                            }
                        ]
                    },
                    "orderBy": [
                        {
                            "column": {
                                "table": {
                                    "alias": "o"
                                },
                                "name": "order_date"
                            },
                            "direction": "DESC"
                        },
                        {
                            "column": {
                                "table": {
                                    "alias": "c"
                                },
                                "name": "customer_name"
                            },
                            "direction": "ASC"
                        }
                    ],
                    "pagination": {
                        "limit": 100,
                        "offset": 0
                    }
                }
                """;
    }

    @After
    public void tearDown() {
        ReportQuery query = gson.fromJson(rawSQL, ReportQuery.class);
        new QueryGenerationImpl().buildSQL(query, DSL.using(SQLDialect.MYSQL));
        System.out.println("--------------------------");
    }
}