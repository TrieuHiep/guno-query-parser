select "o"."status", sum("o"."amount") as "sum_amount"
from "orders" as "o"
group by "o"."status"
having ("o"."status" = ? or "o"."status" = ?)
offset ? rows fetch next ? rows only
