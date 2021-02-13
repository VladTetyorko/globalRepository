DELETE FROM COURSES;
CREATE TABLE IF NOT EXISTS COURSES(
	COURSE_ID serial,
	COURSE_NAME  varchar(255),
	COURSE_DESCRIPTION  varchar(255),
UNIQUE(COURSE_NAME)
);