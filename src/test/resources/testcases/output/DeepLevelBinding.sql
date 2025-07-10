select *
from (select *
      from (select * from "orders" as "o" where "o"."status" = ?) as "level2"
      where "level2"."amount" > ?) as "level1"
where "level1"."count" > ?
offset ? rows fetch next ? rows only
