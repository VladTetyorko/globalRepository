package com.census.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.census.entities.SettingValue;
import com.census.entities.User;
import com.census.repositories.CustomSettingRepository;

@Service
public class CustomSettingService {

	private final CustomSettingRepository repository;

	private static final Logger logger = LoggerFactory.getLogger(CustomSettingService.class.getName());

	public CustomSettingService(CustomSettingRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public SettingValue save(SettingValue setting) {
		SettingValue saved = null;
		saved = repository.save(setting);
		logger.info("Custom setting propety {} saved", saved.toString());
		return saved;
	}

	@Transactional
	public Optional<SettingValue> find(int id) {
		Optional<SettingValue> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching custom setting with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("Custom setting with id {} was found successfull", id);
		else
			logger.warn("Custom setting with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public List<SettingValue> findAll() {
		List<SettingValue> list = new ArrayList<SettingValue>();
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

	@Transactional
	public List<SettingValue> findForUser(User user) {
		List<SettingValue> founded = new ArrayList<SettingValue>();
		if (logger.isDebugEnabled())
			logger.debug("Searching settings for user {}", user.getUsername());
		founded = repository.findByUser(user);
		if (!founded.isEmpty())
			logger.info("Settings for user {} was found successfull", user.getUsername());
		else {
			logger.warn("Settings for user {} doesnt exsist", user.getUsername());
		}
		return founded;
	}

	@Transactional
	public List<SettingValue> save(List<SettingValue> list) {
		list.forEach(s -> {
			System.out.print(s + "/n/n/n");
			repository.save(s);
		});
		return list;
	}
}