package com.census.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.census.entities.Setting;
import com.census.repositories.GlobalSettingRepository;

@Service
public class GlobalSettingService {

	private final GlobalSettingRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(GlobalSettingRepository.class.getName());

	public GlobalSettingService(GlobalSettingRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Setting save(Setting setting) {
		Setting saved = null;
		saved = repository.save(setting);
		logger.info("Global setting propety {} saved", saved.toString());
		return saved;
	}

	@Transactional
	public Optional<Setting> find(int id) {
		Optional<Setting> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching setting with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Setting with id {} was found successfull", id);
		else
			logger.warn("Setting with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public Optional<Setting> findByName(String name) {
		Optional<Setting> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching setting with name {}", name);
		founded = repository.findByName(name);
		if (founded.isPresent())
			logger.info("Setting with name {} was found successfull", name);
		else
			logger.warn("Setting with name {} doesnt exsist", name);
		return founded;
	}

	@Transactional
	public List<Setting> findAll() {
		List<Setting> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all categories");
		list = repository.findAll();
		logger.info("Setting list was found");
		return list;
	}

	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleating setting with id={}", id);
		try {
			repository.deleteById(id);
			logger.info("Setting with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	public Optional<Setting> findByPartOfName(String partOfName) {
		Optional<Setting> list;
		if (logger.isDebugEnabled())
			logger.debug("Searching all categories contains {}", partOfName);
		list = repository.findByName(partOfName);
		logger.info("Setting list contains '{}' was found", partOfName);
		return list;
	}
}