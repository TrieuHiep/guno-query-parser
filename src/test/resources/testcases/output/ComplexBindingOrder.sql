select count("sub1"."id") as "count"
from (select * from "orders" as "o" where "o"."status" = ?) as "sub1"
         join (select * from "customers" as "c" where "c"."country" = ?) as "sub2" on "sub1"."customer_id" = "sub2"."id"
where "sub1"."amount" > ?
offset ? rows fetch next ? rows only
