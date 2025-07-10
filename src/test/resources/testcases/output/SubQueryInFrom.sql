select "sub_orders"."region_id", sum("sub_orders"."total_price") as "sum_price"
from (select "o"."region_id", sum("o"."price") as "total_price"
      from "orders" as "o"
      group by "o"."region_id") as "sub_orders"
group by "sub_orders"."region_id"
offset ? rows fetch next ? rows only
