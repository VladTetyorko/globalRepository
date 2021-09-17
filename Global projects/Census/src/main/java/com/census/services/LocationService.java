package com.census.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.census.entities.Location;
import com.census.exceptions.LocationAlreadyExsistException;
import com.census.exceptions.SQLUnexpectedException;
import com.census.repositories.LocationRepository;

@Service
public class LocationService {

	private final LocationRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(LocationService.class.getName());

	public LocationService(LocationRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Location save(Location location) {
		Location saved = null;
		try {
			saved = repository.save(location);
			logger.info("Location {} saved", saved.toString());
			return saved;
		} catch (JpaSystemException e) {
			logger.warn(e.getRootCause().getLocalizedMessage());
			if (e.getRootCause().getLocalizedMessage().contains("SQLITE_CONSTRAINT_UNIQUE"))
				throw new LocationAlreadyExsistException(location.getName());
			else
				throw new SQLUnexpectedException(e.getRootCause().getLocalizedMessage());
		}

	}

	@Transactional
	public Optional<Location> find(int id) {
		Optional<Location> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching user with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Location with id {} was found successfull", id);
		else
			logger.warn("Location with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public List<Location> findAll() {
		List<Location> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all locations");
		list = repository.findAll();
		logger.info("Location list was found");
		return list;
	}

	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting user with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("Location with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Transactional
	public Optional<Location> findByName(String name) {
		Optional<Location> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching user with id {}", name);
		founded = repository.findByName(name);
		if (founded.isPresent())
			logger.info("Location with id {} was found successfull", name);
		else
			logger.warn("Location with id {} doesnt exsist", name);
		return founded;
	}

	@Transactional
	public List<Location> findByPartOfName(String partOfName) {
		List<Location> list = new ArrayList<Location>();
		if (logger.isDebugEnabled())
			logger.debug("Searching all locations contains {}", partOfName);
		list = repository.findTop20ByNameIsContaining(partOfName);
		logger.info("Location list contains '{}' was found", partOfName);
		return list;
	}

	@Transactional
	public List<Location> searchByNameAndLimitCustomQuerry(String partOfName, int limit) {
		List<Location> list = new ArrayList<Location>();
		list = repository.findLocationsCustomByOrder(partOfName, limit);
		return list;
	}

}
