package com.foxminded.university.forms.formatters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.foxminded.university.entities.Lecture;
import com.foxminded.university.entities.personDetails.Teacher;
import com.foxminded.university.forms.LectureForm;
import com.foxminded.university.services.CourseService;
import com.foxminded.university.services.GroupService;
import com.foxminded.university.services.PersonService;

@Component
public class LectureFormFormatter {
	@Autowired
	GroupService groupService;
	@Autowired
	PersonService personService;
	@Autowired
	CourseService courseServise;

	public Lecture getLecture(LectureForm lectureForm) {
		Lecture returning = new Lecture();
	    returning.setLectureId(lectureForm.getLectureId());
		returning.setTimeSlot(lectureForm.getTimeSlot());
		returning.setAudience(lectureForm.getRoomNumber());
		returning.setCourse(courseServise.find(lectureForm.getCourseId()).get());
		returning.setGroup(groupService.find(lectureForm.getGroupId()).get());
		returning.setTeacher((Teacher) personService.find(lectureForm.getTeacherId()).get());
		returning.setWeek(lectureForm.getWeek());
		returning.setWeekday(lectureForm.getWeekday());
		return returning;
	}

}
