select "o"."region", count("o"."id") as "total_orders"
from "orders" as "o"
         join (select "c"."id", count("c"."id") as "order_count"
               from "customers" as "c"
               group by "c"."id") as "vip_customers" on "o"."customer_id" = "vip_customers"."id"
where ("o"."status" = ? and ("o"."country" = ? or "o"."country" = ?) and not ("o"."payment_method" = ?))
group by "o"."region"
order by "o"."region" asc
offset ? rows fetch next ? rows only
