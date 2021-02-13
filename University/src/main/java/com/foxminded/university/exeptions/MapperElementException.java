package com.foxminded.university.exeptions;

public class MapperElementException extends UniversityDaoException {

	public MapperElementException(String string, Object entity) {
		super(string, entity);
	}

}
