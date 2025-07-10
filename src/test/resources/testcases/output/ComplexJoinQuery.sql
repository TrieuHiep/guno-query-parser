select "o"."order_number", "c"."customer_name", "a"."street_address", "co"."country_name", "p"."product_name"
from "orders" as "o"
         join "customers" as "c" on "o"."customer_id" = "c"."id"
         left outer join "addresses" as "a" on "c"."shipping_address_id" = "a"."id"
         join "countries" as "co" on "a"."country_id" = "co"."id"
         join "order_items" as "oi" on "o"."id" = "oi"."order_id"
         join "products" as "p" on "oi"."product_id" = "p"."id"
where ("o"."status" = ? and "o"."order_date" > ? and ("co"."country_name" = ? or "co"."country_name" = ?) and
       "p"."price" > ?)
order by "o"."order_date" desc, "c"."customer_name" asc
offset ? rows fetch next ? rows only
