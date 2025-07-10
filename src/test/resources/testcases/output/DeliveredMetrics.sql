select "s"."channel", SUM(s.orders) / SUM(s.visits) as "conversion_rate"
from "sessions" as "s"
group by "s"."channel"
offset ? rows fetch next ? rows only
