package com.census.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.census.entities.Category;
import com.census.entities.Item;
import com.census.entities.Location;
import com.census.repositories.ItemRepository;

@SpringBootTest
class ItemServiceTest {
	@MockBean
	private ItemRepository repository;
	@Autowired
	private ItemService service;

	private Category category = new Category("category");
	private Location location = new Location("location");
	private Item item1 = new Item(1, "testname1", "description", category, location, null);
	private Item item2 = new Item(2, "testname2", "description", category, location, null);
	private List<Item> list = new ArrayList<Item>();

	@BeforeEach
	private void setup() {
		list.add(item1);
		list.add(item2);
		Mockito.when(repository.findById(1)).thenReturn(Optional.of(item1));
		Mockito.when(repository.findByName("testName2")).thenReturn(Optional.of(item2));
		Mockito.when(repository.findByCategory(category)).thenReturn(list);
		Mockito.when(repository.findByLocation(location)).thenReturn(list);
	}

	@Test
	final void shouldFindWhenLegalId() {
		assertEquals(item1, service.find(1).get());
		verify(repository, times(1)).findById(1);
	}

	@Test
	final void shouldFindWhenLegalName() {
		assertEquals(item2, service.findByName("testName2").get());
		verify(repository, times(1)).findByName(Mockito.any());
	}

	@Test
	final void shouldFindListByCategory() {
		assertEquals(list, service.findForCategory(category));
		verify(repository, times(1)).findByCategory(category);
	}

	@Test
	final void shouldFindListByLocation() {
		assertEquals(list, service.findForLocation(location));
		verify(repository, times(1)).findByLocation(location);
	}

	@Test
	final void shouldReturnEmptyListWhenLocationDoesntExists() {
		Location l = new Location("NewName");
		assertEquals(true, service.findForLocation(l).isEmpty());
		verify(repository, times(1)).findByLocation(l);
	}

	@Test
	final void shouldReturnEmptyListWhenCategoryDoesntExists() {
		Category c = new Category("NewName");
		assertEquals(true, service.findForCategory(c).isEmpty());
		verify(repository, times(1)).findByCategory(c);
	}

}
