INSERT INTO COURSES
(
   COURSE_NAME,
   COURSE_DESCRIPTION
)
VALUES
(
   'c1',
   'desc1'
),

(
   'c2',
   'desc2'
),

(
   'c3',
   'desc3'
);
INSERT INTO UNIVERSITY_GROUPS (GROUP_NAME) 
VALUES 
('g1'),
('g2'),
('g3');
Insert INTO PERSONS
(
   NAME,
   USERS_PASSWORD,
   FACULTY,
   ROLE
)
VALUES
(
   'S_name1',
   'password1',
   'faculty',
   'STUDENT'
),

(
   'S_name2',
   'password2',
   'faculty',
   'STUDENT'
),

(
   'S_name3',
   'password3',
   'faculty',
   'STUDENT'
),

(
   'T_name4',
   'password4',
   'faculty',
   'TEACHER'
),
(
   'A_name5',
   'password5',
   'faculty',
   'ADMIN'
);
INSERT INTO STUDENTS
(
   PERSON_ID,
   GROUP_ID
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
INSERT INTO TEACHERS
(
   PERSON_ID,
   ACADEMIC_DEGREE
)
VALUES
(
   4,
   'docent'
);

INSERT INTO ADMINS
(
   PERSON_ID,
   POSITION
)
VALUES
(
   5,
   'Middle'
);

Insert Into LECTURES
(
   TIMESLOT,
   TEACHER_ID,
   COURSE_ID,
   GROUP_ID,
   ROOM_NUMBER,
   WEEK,
   WEEKDAY)
VALUES
(
   1,
   4,
   1,
   1,
   1,
   1,
   'WEDNESDAY'
),

(
   2,
   4,
   2,
   2,
   1,
   1,
   'WEDNESDAY'
),

(
   3,
   4,
   2,
   3,
   1,
   1,
   'FRIDAY'
),

(
   4,
   4,
   1,
   2,
   1,
   1,
   'FRIDAY'
),

(
   3,
   4,
   2,
   3,
   2,
   1,
   'WEDNESDAY');