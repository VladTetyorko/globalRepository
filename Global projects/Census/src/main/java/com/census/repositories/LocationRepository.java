package com.census.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.census.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

	public Optional<Location> findByName(String name);

	public List<Location> findTop20ByNameIsContaining(String partOfName);

	@Query(value = "select ID, Name from Locations where NAME LIKE %?1% limit ?2", nativeQuery = true)
	public List<Location> findLocationsCustomByOrder(String partOfName, int limit);

}
