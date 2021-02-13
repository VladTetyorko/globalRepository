package com.foxminded.warehouse.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.warehouse.entities.Category;
import com.foxminded.warehouse.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	public Optional<Product> findByName(String name);

	public List<Product> findByCategory(Category category);

	public Optional<Product> findByCategoryAndName(Category category, String name);

}
