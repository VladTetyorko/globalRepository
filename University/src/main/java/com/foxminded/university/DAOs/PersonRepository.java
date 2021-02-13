package com.foxminded.university.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foxminded.university.entities.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>  {
}
