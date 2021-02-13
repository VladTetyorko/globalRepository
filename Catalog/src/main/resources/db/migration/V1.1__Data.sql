INSERT INTO PROPERTIES (NAME) VALUES ('CPU'),('GPU');

INSERT INTO PRODUCTS (PRODUCT_NAME) VALUES ('Lenovo-Y550'),('Lenovo-Y750'),('Xiaomi Redmi Note 8');

INSERT INTO PROPERTY_VALUES
(
   PRODUCT_ID,
   PROPERTY_ID,
   PROPERTY_VALUE
)
VALUES
(
   1,
   1,
   'Intel core i8'
),
(
   1,
   2,
   'GTX 1080ti'
),
(
   2,
   1,
   'Intel core i9 9080'
),
(
   2,
   2,
   'GTX 1660'
),
(
   3,
   1,
   'Snapdragon 750'
),
(
   3,
   2,
   'integrated'
);

INSERT INTO CATEGORIES( CATEGORY_NAME ) VALUES ('laptops'), ('smartphones');

INSERT INTO CATEGORY_PRODUCT (CATEGORY_ID,PRODUCT_ID) VALUES (1,1), (1,2),(2,3);

INSERT INTO USERS (USERNAME,PASSWORD,ROLE) VALUES ('user','$2a$04$WJ3nsZDnhTmJWPh.Al272.kREBQFLnSi3LtB.1s.T2WKq6.Pjs3tS','USER')
,('admin','$2a$04$RwJiVDXGiPdPNb6/xwB5XuC7VVcIpddiwQItnLOAlf0E4SgVYL4Zi','ADMIN');

