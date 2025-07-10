select "c"."country", sum("o"."amount") as "revenue"
from "orders" as "o"
         join "customers" as "c" on "o"."customer_id" = "c"."id"
group by "c"."country"
offset ? rows fetch next ? rows only
