package com.census.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.census.entities.Category;
import com.census.entities.Item;
import com.census.entities.Location;
import com.census.entities.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	public Optional<Item> findByName(String name);

	public List<Item> findByOwner(User user);

	public List<Item> findByLocation(Location location);

	public List<Item> findByCategory(Category category);

	public List<Item> findByLocationAndNameIsContaining(Location location, String partOfName);

	public List<Item> findByCategoryAndNameIsContaining(Category category, String partOfName);

	public List<Item> findByOwnerAndNameIsContaining(User principal, String partOfName);

}
