package com.test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.calendar.entities.Date;

@Repository
public interface DateRepository extends JpaRepository<Date, Integer> {

	public List<Date> findByMonthAndYear(int month, int year);

	public Optional<Date> findByDayAndMonthAndYear(int day, int month, int year);

}