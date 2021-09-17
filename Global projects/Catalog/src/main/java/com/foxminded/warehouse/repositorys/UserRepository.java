package com.foxminded.warehouse.repositorys;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.foxminded.warehouse.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public Optional<User> findByUsername(String username);

	@Query(value = "Select u.username, u.role from USERS u", nativeQuery = true)
	public List<Object> findForAdmin();

}
