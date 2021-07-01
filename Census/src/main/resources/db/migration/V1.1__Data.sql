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
INSERT INTO ITEMS
(
   NAME,
   DESCRIPTION
)
VALUES
(
   'Dishwasher',
   'Best dishwasher in your city'
),

(
   'Bottle',
   'Just a bottle of beer'
),

(
   'Something',
   'No one know what is it'
);

INSERT INTO GLOBAL_SETTINGS( 
NAME
)
VALUES 
('Limit on list'),
('Limit on page');

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