package com.foxminded.warehouse.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.warehouse.entities.Product;
import com.foxminded.warehouse.entities.Property;
import com.foxminded.warehouse.entities.PropertyValue;

@Repository
public interface PropertyValueRepository extends JpaRepository<PropertyValue, Integer> {

	public Optional<PropertyValue> findByGlobalPropertyAndProduct(Property glodalProperty, Product product);

	public List<PropertyValue> findByProduct(Product product);

}
