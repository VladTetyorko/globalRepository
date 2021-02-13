package com.foxminded.university.form;

import java.time.DayOfWeek;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.university.entities.Course;
import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.Role;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.forms.LectureForm;
import com.foxminded.university.forms.formatters.LectureFormFormatter;
import com.foxminded.university.services.CourseService;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class LectureFormTest {
	@MockBean
	GroupService groupService;
	@MockBean
	PersonService personService;
	@MockBean
	CourseService courseService;
	@Autowired
	LectureFormFormatter lfFormater;

	LectureForm lf = new LectureForm(1, 1, 1, 2, 3, 121, 1, DayOfWeek.MONDAY);
	Group g3=new Group();
	Course c2=new Course();
	Teacher t1=new Teacher(1, "Teacher", "p", "faculty", Role.TEACHER, "docent");
	
	@BeforeEach
	void setup() {
		g3.setId(3);
		g3.setGroupName("gname");
		
		c2.setCourseId(2);
		c2.setCourseName("CName");
		c2.setCourseDescription("cDesc");
		
		Mockito.when(groupService.find(3)).thenReturn(Optional.of(g3));
		Mockito.when(courseService.find(2)).thenReturn(Optional.of(c2));
		Mockito.when(personService.find(1)).thenReturn(Optional.of(t1));
	}

	@Test
	void shouldReturnStudent() {
		Lecture shouldBeLecture=lfFormater.getLecture(lf);
		assertEquals(shouldBeLecture.getAudience().intValue(),121);
		assertEquals(shouldBeLecture.getGroup().getId(), g3.getId());
		assertEquals(shouldBeLecture.getCourse().getCourseName(), c2.getCourseName());
		assertEquals(shouldBeLecture.getTeacher().getName(), t1.getName());
		verify(groupService,times(1)).find(3);
		verify(courseService,times(1)).find(2);
		verify(personService,times(1)).find(1);
	}

}