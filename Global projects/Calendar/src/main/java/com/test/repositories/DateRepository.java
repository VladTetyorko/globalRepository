package com.test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.calendar.entities.DateEntity;

@Repository
public interface DateRepository extends JpaRepository<DateEntity, Integer> {

	public List<DateEntity> findByMonthAndYear(int month, int year);

	public Optional<DateEntity> findByDayAndMonthAndYear(int day, int month, int year);

}