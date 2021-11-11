package com.census.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.census.entities.SomeEntity;
@Repository
public interface SomeEntityRepository extends JpaRepository<SomeEntity, Integer> {
}