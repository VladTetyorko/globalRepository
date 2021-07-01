package com.census.forms.formatters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.census.entities.Pair;

class TableFormatterTest {

	@Test
	final void shuoldReturnPairOfSublistAndPages() {
		List<String> list = new ArrayList<String>();
		list.add("String1");
		list.add("String2");
		list.add("String3");
		list.add("String4");
		list.add("String5");

		TableFormatter formatter = new TableFormatter();
		Pair p1 = formatter.getSublist(list, 1, 1);
		Pair p2 = formatter.getSublist(list, 5, 1);
		assertEquals(5, p1.size);
		assertEquals(1, ((List) p1.list).size());

		assertEquals(1, p2.size);
		assertEquals(5, ((List) p2.list).size());

	}

}
