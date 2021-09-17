package com.foxminded.university.exeptions;

import com.foxminded.university.entities.Lecture;

public class UniversityDaoSaveException extends UniversityDaoException {

	public UniversityDaoSaveException(String message, Object model) {
		super(message,model);
	}

}
