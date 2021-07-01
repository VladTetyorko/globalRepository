package com.census.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.census.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Optional<Category> findByName(String name);

	public List<Category> findTop10ByNameIsContaining(String partOfName);

	@Query(value = "select ID, Name from Categories where NAME LIKE %?1% limit ?2", nativeQuery = true)
	public List<Category> findLocationsCustomByOrder(String categoryNamePart, int limit);

}