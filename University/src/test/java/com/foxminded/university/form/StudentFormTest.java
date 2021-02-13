package com.foxminded.university.form;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.foxminded.university.entities.Group;
import com.foxminded.university.entities.personDetails.Student;
import com.foxminded.university.forms.StudentForm;
import com.foxminded.university.forms.formatters.StudentFormFormatter;
import com.foxminded.university.services.GroupService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StudentFormTest {
	@Autowired
	private StudentFormFormatter sfFormater;
	@MockBean
	private GroupService groupService;

	StudentForm sf = new StudentForm(1, "0", "pass", "faculty", 1);
	Group g1 = new Group();
	
	@BeforeEach
	void setup() {
		g1.setId(1);
		g1.setGroupName("gname");
		Mockito.when(groupService.find(1)).thenReturn(Optional.of(g1));
	}

	@Test
	void shouldReturnStudent() {
		Student shouldBeStudent=sfFormater.getStudent(sf);
		assertEquals(shouldBeStudent.getId(),sf.getId());
		assertEquals(shouldBeStudent.getGroup().getId(), g1.getId());
		verify(groupService,times(1)).find(1);
	}

}
