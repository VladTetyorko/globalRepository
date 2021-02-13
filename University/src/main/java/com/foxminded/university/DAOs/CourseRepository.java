package com.foxminded.university.DAOs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foxminded.university.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
