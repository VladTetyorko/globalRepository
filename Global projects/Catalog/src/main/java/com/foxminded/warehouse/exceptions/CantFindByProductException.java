package com.foxminded.warehouse.exceptions;

public class CantFindByProductException extends CantFindByCategoryException {

	private String productName;

	public CantFindByProductException(String categoryName, String productName) {
		super(categoryName);
		this.productName = productName;
	}

	@Override
	public String getMessage() {
		StringBuffer builder = new StringBuffer("Cant find in category=");
		builder.append(super.getCategoryName());
		builder.append(" product=");
		builder.append(productName);
		return builder.toString();
	}

	public String getProduct() {
		return productName;
	}
}
