package com.foxminded.warehouse.repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.warehouse.entities.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {
	public Optional<Property> findByName(String name);

}
