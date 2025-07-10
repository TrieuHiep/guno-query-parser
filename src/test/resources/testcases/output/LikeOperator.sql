select "c"."name"
from "customers" as "c"
where cast("c"."email" as varchar) like ?
offset ? rows fetch next ? rows only
