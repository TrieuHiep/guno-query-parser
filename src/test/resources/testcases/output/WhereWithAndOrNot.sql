select count("o"."id") as "count"
from "orders" as "o"
where ("o"."country" = ? and ("o"."status" = ? or "o"."total" > ?) and not ("o"."payment_method" = ?))
offset ? rows fetch next ? rows only
