package com.foxminded.university.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.foxminded.university.entities.Lecture;

@Repository
public interface ScheduleRepository extends JpaRepository<Lecture, Integer>,ScheduleRepositoryCustom {

}
