package com.census.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.census.entities.User;
import com.census.exceptions.AlreadyExsistException;
import com.census.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository repository;

	private final PasswordEncoder bCryptPasswordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

	public UserService(PasswordEncoder bCryptPasswordEncoder, UserRepository repository) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.repository = repository;
	}

	@Transactional
	public User save(User user) throws AlreadyExsistException {
		User saved = null;
		Optional<User> founded = findUserByUsername(user.getUsername());
		if (founded.isPresent())
			throw new AlreadyExsistException(user.getUsername());
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		saved = repository.save(user);
		logger.info("User {} saved", saved.toString());
		return saved;
	}

	@Transactional
	public Optional<User> find(int id) {
		Optional<User> founded = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching user with id {}", id);
		founded = repository.findById(id);
		if (founded.isPresent())
			logger.info("User with id {} was found successfull", id);
		else
			logger.warn("User with id {} doesnt exsist", id);
		return founded;
	}

	@Transactional
	public List<User> findAll() {
		List<User> list = null;
		if (logger.isDebugEnabled())
			logger.debug("Searching all users");
		list = repository.findAll();
		logger.info("User list was found");
		return list;
	}

	@Transactional
	public boolean delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting user with id {}", id);
		try {
			repository.deleteById(id);
			logger.info("User with id={} was deleted", id);
			return true;
		} catch (Exception e) {
			logger.warn(e.toString());
			return false;
		}
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Load by username {}", username);
		Optional<User> user = repository.findByUsername(username);

		UserBuilder builder = null;
		if (user.isPresent()) {
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(user.get().getPassword());
			builder.roles("USER");
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}

	@Transactional
	public Optional<User> findUserByUsername(String username) {
		logger.info("findUserByUsername {} ", username);

		if (logger.isDebugEnabled())
			logger.debug("Searching for user with username={}", username);
		try {
			Optional<User> user = repository.findByUsername(username);
			logger.info("User with username={} was founded", username);
			return user;
		} catch (Exception e) {
			logger.warn(e.toString());
			return Optional.empty();
		}
	}

}
