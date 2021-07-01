package com.census.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.census.entities.Setting;

@Repository
public interface GlobalSettingRepository extends JpaRepository<Setting, Integer> {

	public Optional<Setting> findByName(String name);

}