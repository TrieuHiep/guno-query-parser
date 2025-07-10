SELECT count("p"."id") AS "product_count"
FROM "products" AS "p"
GROUP BY "p"."product_name"
OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
