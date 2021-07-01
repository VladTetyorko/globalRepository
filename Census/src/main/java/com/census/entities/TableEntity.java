package com.census.entities;

public abstract class TableEntity {

	public abstract String[] getTableHeader();

	public abstract String[] toTableRow();

}
