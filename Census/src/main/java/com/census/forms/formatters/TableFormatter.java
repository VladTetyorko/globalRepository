package com.census.forms.formatters;

import java.util.ArrayList;
import java.util.List;

import com.census.entities.Pair;

public class TableFormatter<T> {

	public Pair getSublist(List<T> list, int limit, int page) {
		int pages = 1;
		page = page - 1;
		pages = list.size() / limit;
		if (list.size() % limit > 0)
			pages += 1;
		if (page * limit > list.size())
			return new Pair(pages, new ArrayList());
		else if (page * limit > list.size() - limit) {
			return new Pair(pages, list.subList(page * limit, list.size()));
		} else
			return new Pair(pages, list.subList(page * limit, page * limit + limit));
	}

}
