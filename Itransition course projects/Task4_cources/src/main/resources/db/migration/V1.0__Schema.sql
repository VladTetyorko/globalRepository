CREATE TABLE USERS
(
   ID serial,
   USERNAME varchar(256) NOT NULL,
   PASSWORD varchar(256) NOT NULL,
   STATUS varchar(256) NOT NULL DEFAULT 'ACTIVE',
   UNIQUE (USERNAME)
);
