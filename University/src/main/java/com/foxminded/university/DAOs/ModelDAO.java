
package com.foxminded.university.DAOs;

import java.util.List;
import java.util.Optional;

public interface ModelDAO<T> {
	T save(T model);

	T insert(T model);

	T update(T model);

	Optional<T> findById(int id);

	List<T> findAll();

	boolean deleteById(int id);
}
