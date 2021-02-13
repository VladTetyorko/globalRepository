package com.foxminded.warehouse.exceptions;

public class CantFindByCategoryException extends Exception {

	private String categoryName;

	public CantFindByCategoryException(String category) {
		this.categoryName = category;
	}

	@Override
	public String getMessage() {
		StringBuffer builder = new StringBuffer("Cant find category=");
		builder.append(categoryName);
		return builder.toString();
	}

	public String getCategoryName() {
		return categoryName;
	}
}
