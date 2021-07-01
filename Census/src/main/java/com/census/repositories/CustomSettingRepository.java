package com.census.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.census.entities.SettingValue;
import com.census.entities.User;

@Repository
public interface CustomSettingRepository extends JpaRepository<SettingValue, Integer> {

	public List<SettingValue> findByUser(User user);

}