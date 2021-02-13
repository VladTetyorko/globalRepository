package com.foxminded.warehouse.services;

import java.util.List;
import java.util.Optional;


public interface ModelService <T>{

	public T save(T model);

	public Optional<T> find(int id) ;
	
	public List<T> findAll();
	
	public boolean delete(int id);
}
