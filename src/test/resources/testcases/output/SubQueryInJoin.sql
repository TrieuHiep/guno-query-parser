select sum("o"."amount") as "total_amount"
from "orders" as "o"
         join (select "c"."id", count("c"."id") as "order_count"
               from "customers" as "c"
               group by "c"."id") as "sub_cust" on "o"."customer_id" = "sub_cust"."id"
offset ? rows fetch next ? rows only
