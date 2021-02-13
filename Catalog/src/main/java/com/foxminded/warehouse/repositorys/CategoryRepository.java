package com.foxminded.warehouse.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.warehouse.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	public Optional<Category> findByName(String name);

}
