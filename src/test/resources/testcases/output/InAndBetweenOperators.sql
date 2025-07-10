select count("o"."id") as "count"
from "orders" as "o"
where ("o"."status" in (?, ?, ?) and "o"."amount" between ? and ?)
offset ? rows fetch next ? rows only
