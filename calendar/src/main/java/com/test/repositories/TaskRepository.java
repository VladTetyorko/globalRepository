package com.test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.calendar.entities.Date;
import com.test.calendar.entities.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	public List<Task> findByDate(Date date);

}
