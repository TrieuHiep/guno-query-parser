package vn.guno;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import vn.guno.core.BaseCondition;

import static org.junit.Assert.*;

public class QueryGenerationImplTest {

    Gson gson;
    private String rawSQL;

    @Before
    public void setUp() {
        gson = new GsonBuilder()
                .registerTypeAdapter(BaseCondition.class, new BaseConditionDeserializer())
                .create();
    }

    @Test
    public void simpleSelect() {
        rawSQL = """
                {
                  "fromGTable": {
                    "name": "products",
                    "alias": "p"
                  },
                  "dimensions": [
                    {
                      "column": {
                        "table": {
                          "alias": "p"
                        },
                        "name": "product_name"
                      }
                    }
                  ],
                  "metrics": [
                    {
                      "type": "COUNT",
                      "alias": "product_count",
                      "column": {
                        "table": {
                          "alias": "p"
                        },
                        "name": "id"
                      }
                    }
                  ]
                }
                                
                """;
    }

    @Test
    public void whereWithAndOrNot() {
        rawSQL = """
                {
                  "fromGTable": { "name": "orders", "alias": "o" },
                  "metrics": [{ "type": "COUNT", "alias": "count", "column": { "table": { "alias": "o" }, "name": "id" } }],
                  "whereCondition": {
                    "operator": "AND",
                    "conditions": [
                      {
                        "column": { "table": { "alias": "o" }, "name": "country" },
                        "operator": "EQUALS",
                        "value": "US"
                      },
                      {
                        "operator": "OR",
                        "conditions": [
                          {
                            "column": { "table": { "alias": "o" }, "name": "status" },
                            "operator": "EQUALS",
                            "value": "PENDING"
                          },
                          {
                            "column": { "table": { "alias": "o" }, "name": "total" },
                            "operator": "GREATER_THAN",
                            "value": 100
                          }
                        ]
                      },
                      {
                        "operator": "NOT",
                        "conditions": [
                          {
                            "column": { "table": { "alias": "o" }, "name": "payment_method" },
                            "operator": "EQUALS",
                            "value": "CASH"
                          }
                        ]
                      }
                    ]
                  }
                }
                                
                """;
    }

    @Test
    public void joinBetweenTables() {
        rawSQL = """
                {
                    "fromGTable": {
                      "name": "orders",
                      "alias": "o"
                    },
                    "joins": [
                      {
                        "joinType": "INNER",
                        "toTable": {
                          "name": "customers",
                          "alias": "c"
                        },
                        "onCondition": {
                          "column": {
                            "table": {
                              "alias": "o"
                            },
                            "name": "customer_id"
                          },
                          "operator": "EQUALS",
                          "value": "c.id"
                        }
                      }
                    ],
                    "dimensions": [
                      {
                        "column": {
                          "table": {
                            "alias": "c"
                          },
                          "name": "country"
                        }
                      }
                    ],
                    "metrics": [
                      {
                        "type": "SUM",
                        "alias": "revenue",
                        "column": {
                          "table": {
                            "alias": "o"
                          },
                          "name": "amount"
                        }
                      }
                    ]
                  }
                  
                                
                """;
    }

    @Test
    public void havingWithFilters() {
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
                        "name": "status"
                      }
                    }
                  ],
                  "metrics": [
                    {
                      "type": "SUM",
                      "alias": "sum_amount",
                      "column": {
                        "table": {
                          "alias": "o"
                        },
                        "name": "amount"
                      }
                    }
                  ],
                  "havingCondition": {
                    "operator": "OR",
                    "conditions": [
                      {
                        "column": {
                          "table": {
                            "alias": "o"
                          },
                          "name": "status"
                        },
                        "operator": "EQUALS",
                        "value": "paid"
                      },
                      {
                        "column": {
                          "table": {
                            "alias": "o"
                          },
                          "name": "status"
                        },
                        "operator": "EQUALS",
                        "value": "shipped"
                      }
                    ]
                  }
                }
                                
                                
                """;
    }

    @Test
    public void deliveredMetrics() {
        rawSQL = """
                {
                   "fromGTable": {
                     "name": "sessions",
                     "alias": "s"
                   },
                   "dimensions": [
                     {
                       "column": {
                         "table": {
                           "alias": "s"
                         },
                         "name": "channel"
                       }
                     }
                   ],
                   "derivedMetrics": [
                     {
                       "alias": "conversion_rate",
                       "expression": "SUM(s.orders) / SUM(s.visits)"
                     }
                   ]
                 }
                 
                                
                                
                """;
    }

    @Test
    public void distinctAndCount() {
        rawSQL = """
                {
                                  "fromGTable": {
                                    "name": "users",
                                    "alias": "u"
                                  },
                                  "distinct": true,
                                  "dimensions": [
                                    {
                                      "column": {
                                        "table": {
                                          "alias": "u"
                                        },
                                        "name": "country"
                                      }
                                    }
                                  ],
                                  "metrics": [
                                    {
                                      "type": "COUNT_DISTINCT",
                                      "alias": "unique_emails",
                                      "column": {
                                        "table": {
                                          "alias": "u"
                                        },
                                        "name": "email"
                                      }
                                    }
                                  ]
                                }
                                
                                
                """;
    }

    @Test
    public void subQueryInFrom() {
        rawSQL = """
                {
                  "fromGTable": {
                    "alias": "sub_orders",
                    "subquery": {
                      "alias": "sub_orders",
                      "query": {
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
                              "name": "region_id"
                            }
                          }
                        ],
                        "metrics": [
                          {
                            "type": "SUM",
                            "alias": "total_price",
                            "column": {
                              "table": {
                                "alias": "o"
                              },
                              "name": "price"
                            }
                          }
                        ],
                        "whereConditions": [
                          {
                            "column": {
                              "table": {
                                "alias": "o"
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
                  "dimensions": [
                    {
                      "column": {
                        "table": {
                          "alias": "sub_orders"
                        },
                        "name": "region_id"
                      }
                    }
                  ],
                  "metrics": [
                    {
                      "type": "SUM",
                      "alias": "sum_price",
                      "column": {
                        "table": {
                          "alias": "sub_orders"
                        },
                        "name": "total_price"
                      }
                    }
                  ]
                }
                                 
                """;
    }

    @Test
    public void subQueryInJoin() {
        rawSQL = """
                {
                  "fromGTable": {
                    "name": "orders",
                    "alias": "o"
                  },
                  "joins": [
                    {
                      "joinType": "INNER",
                      "toSubquery": {
                        "alias": "sub_cust",
                        "query": {
                          "fromGTable": {
                            "name": "customers",
                            "alias": "c"
                          },
                          "dimensions": [
                            {
                              "column": {
                                "table": {
                                  "alias": "c"
                                },
                                "name": "id"
                              }
                            }
                          ],
                          "metrics": [
                            {
                              "type": "COUNT",
                              "alias": "order_count",
                              "column": {
                                "table": {
                                  "alias": "c"
                                },
                                "name": "id"
                              }
                            }
                          ],
                          "groupBy": [
                            {
                              "column": {
                                "table": {
                                  "alias": "c"
                                },
                                "name": "id"
                              }
                            }
                          ]
                        }
                      },
                      "onCondition": {
                        "column": {
                          "table": {
                            "alias": "o"
                          },
                          "name": "customer_id"
                        },
                        "operator": "EQUALS",
                        "value": "sub_cust.id"
                      }
                    }
                  ],
                  "metrics": [
                    {
                      "type": "SUM",
                      "alias": "total_amount",
                      "column": {
                        "table": {
                          "alias": "o"
                        },
                        "name": "amount"
                      }
                    }
                  ]
                }
                                 
                """;
    }

    @Test
    public void nestedJoinWithSubQuery() {
        rawSQL = """
                {
                                   "fromGTable": {
                                     "name": "orders",
                                     "alias": "o"
                                   },
                                   "joins": [
                                     {
                                       "joinType": "INNER",
                                       "toSubquery": {
                                         "alias": "vip_customers",
                                         "query": {
                                           "fromGTable": {
                                             "name": "customers",
                                             "alias": "c"
                                           },
                                           "metrics": [
                                             {
                                               "type": "COUNT",
                                               "alias": "order_count",
                                               "column": {
                                                 "table": {
                                                   "alias": "c"
                                                 },
                                                 "name": "id"
                                               }
                                             }
                                           ],
                                           "dimensions": [
                                             {
                                               "column": {
                                                 "table": {
                                                   "alias": "c"
                                                 },
                                                 "name": "id"
                                               }
                                             }
                                           ],
                                           "groupBy": [
                                             {
                                               "column": {
                                                 "table": {
                                                   "alias": "c"
                                                 },
                                                 "name": "id"
                                               }
                                             }
                                           ]
                                         }
                                       },
                                       "onCondition": {
                                         "column": {
                                           "table": {
                                             "alias": "o"
                                           },
                                           "name": "customer_id"
                                         },
                                         "operator": "EQUALS",
                                         "value": "vip_customers.id"
                                       }
                                     }
                                   ],
                                   "dimensions": [
                                     {
                                       "column": {
                                         "table": {
                                           "alias": "o"
                                         },
                                         "name": "region"
                                       }
                                     }
                                   ],
                                   "metrics": [
                                     {
                                       "type": "COUNT",
                                       "alias": "total_orders",
                                       "column": {
                                         "table": {
                                           "alias": "o"
                                         },
                                         "name": "id"
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
                                         "operator": "OR",
                                         "conditions": [
                                           {
                                             "column": {
                                               "table": {
                                                 "alias": "o"
                                               },
                                               "name": "country"
                                             },
                                             "operator": "EQUALS",
                                             "value": "US"
                                           },
                                           {
                                             "column": {
                                               "table": {
                                                 "alias": "o"
                                               },
                                               "name": "country"
                                             },
                                             "operator": "EQUALS",
                                             "value": "UK"
                                           }
                                         ]
                                       },
                                       {
                                         "operator": "NOT",
                                         "conditions": [
                                           {
                                             "column": {
                                               "table": {
                                                 "alias": "o"
                                               },
                                               "name": "payment_method"
                                             },
                                             "operator": "EQUALS",
                                             "value": "cash"
                                           }
                                         ]
                                       }
                                     ]
                                   },
                                   "orderBy": [
                                     {
                                       "column": {
                                         "table": {
                                           "alias": "o"
                                         },
                                         "name": "region"
                                       },
                                       "direction": "ASC"
                                     }
                                   ]
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