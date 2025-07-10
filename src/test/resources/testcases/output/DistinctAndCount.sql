select distinct "u"."country", count(distinct "u"."email") as "unique_emails"
from "users" as "u"
group by "u"."country"
offset ? rows fetch next ? rows only
