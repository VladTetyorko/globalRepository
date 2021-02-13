package com.foxminded.warehouse.exceptions;

public class CantFindByPropertyException extends CantFindByProductException {

	private String propertyName;

	public CantFindByPropertyException(String categoryName, String productName, String propertyName) {
		super(categoryName, productName);
		this.propertyName = propertyName;
	}

	@Override
	public String getMessage() {
		StringBuffer builder = new StringBuffer("Cant find in category=");
		builder.append(super.getCategoryName());
		builder.append(" product=");
		builder.append(super.getProduct());
		builder.append(" property=");
		builder.append(propertyName);

		return builder.toString();
	}

}
