select "o"."order_number",
       "c"."customer_name",
       "a"."street_address",
       "co"."country_name",
       "customer_stats"."total_orders",
       "customer_stats"."lifetime_value",
       sum("oi"."total_price")            as "total_order_value",
       count("oi"."id")                   as "total_items",
       avg("p"."price")                   as "avg_item_price",
       max("p"."price")                   as "max_product_price",
       count(distinct "p"."id")           as "unique_products",
       SUM(oi.total_price) / COUNT(oi.id) as "avg_order_value_per_item",
       MAX(p.price) - MIN(p.price)        as "price_variance"
from "orders" as "o"
         join "customers" as "c" on "o"."customer_id" = "c"."id"
         left outer join "addresses" as "a" on "c"."shipping_address_id" = "a"."id"
         join "countries" as "co" on "a"."country_id" = "co"."id"
         join "order_items" as "oi" on "o"."id" = "oi"."order_id"
         join "products" as "p" on "oi"."product_id" = "p"."id"
         join (select "sub_o"."customer_id",
                      count("sub_o"."id")         as "total_orders",
                      sum("sub_o"."total_amount") as "lifetime_value",
                      avg("sub_o"."total_amount") as "avg_order_amount"
               from "orders" as "sub_o"
               where "sub_o"."status" = ?
               group by "sub_o"."customer_id") as "customer_stats" on "c"."id" = "customer_stats"."customer_id"
where ("o"."status" = ? and "o"."order_date" > ? and ("co"."country_name" = ? or "co"."country_name" = ?) and
       "customer_stats"."lifetime_value" > ? and "customer_stats"."total_orders" >= ?)
group by "o"."order_number", "c"."customer_name", "a"."street_address", "co"."country_name",
         "customer_stats"."total_orders", "customer_stats"."lifetime_value"
order by "o"."order_date" desc, "c"."customer_name" asc
offset ? rows fetch next ? rows only
