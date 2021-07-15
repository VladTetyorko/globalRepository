INSERT INTO USERS
(
   USERNAME,
   PASSWORD
)
VALUES
(
   'John',
   '$2y$12$03oJ9VUG31WlQzbRVj.qcOCXB5pd.4lbQX4xwDmfajwTH2k2e2gA.'
),

(
   'Lisa',
   '$2y$12$03oJ9VUG31WlQzbRVj.qcOCXB5pd.4lbQX4xwDmfajwTH2k2e2gA.'
),

(
   'Obama',
   '$2y$12$03oJ9VUG31WlQzbRVj.qcOCXB5pd.4lbQX4xwDmfajwTH2k2e2gA.'
);
INSERT INTO LOCATIONS (NAME) VALUES ('Appartments'),
('Kitchen'),
('Backyard');
INSERT INTO CATEGORIES (NAME) VALUES ('Instruments'),
('Appliances'),
('Сlothes');
INSERT INTO ITEMS (ID) VALUES (1),
(2),
(3);
INSERT INTO GLOBAL_SETTINGS (NAME) VALUES ('Limit on list'),
('Limit on page');
INSERT INTO I18N_SETTINGS
(
   LANGUAGE,
   TRANSLATION,
   SETTING_ID
)
VALUES
(
   'en',
   'Limit on list',
   1
),

(
   'ru',
   'Кол-во записей в выпадающем списке',
   1
),

(
   'en',
   'Limit on page',
   2
),

(
   'ru',
   'Кол-во записей на странице',
   2
);
INSERT INTO I18N_ITEMS
(
   LANGUAGE,
   TRANSLATION_NAME,
   TRANSLATION_DESCRIPTION,
   ITEM_ID
)
VALUES
(
   'en',
   'Product 1',
   'Desc 1',
   1
),

(
   'en',
   'Product 2',
   'Desc 2',
   2
),

(
   'en',
   'Product 3',
   'Desc 3',
   3
),

(
   'ru',
   'Продукт 1',
   'Описание 1',
   1
),

(
   'ru',
   'Продукт 2',
   'Описание 2',
   2
),

(
   'ru',
   'Продукт 3',
   'Описание 3',
   3
),

(
   'ua',
   'Продукт 1 укр',
   'Описание 1 укр',
   1
),

(
   'ua',
   'Продукт 2 укр',
   'Описание 2 укр',
   2
),

(
   'ua',
   'Продукт 3 укр',
   'Описание 3 укр',
   3
);
INSERT INTO ITEM_CATEGORY
(
   ITEM_ID,
   CATEGORY_ID
)
VALUES
(
   1,
   2
),

(
   2,
   1
),

(
   3,
   3
);
INSERT INTO ITEM_LOCATION
(
   ITEM_ID,
   LOCATION_ID
)
VALUES
(
   1,
   3
),

(
   2,
   2
),

(
   3,
   1
);
INSERT INTO ITEM_USER
(
   ITEM_ID,
   USER_ID
)
VALUES
(
   1,
   1
),

(
   2,
   2
),

(
   3,
   3
);