package com.itrans.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itrans.entities.User;
import com.itrans.reporitories.UserRepository;

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
	public User save(User user) {
		User saved = null;
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		if (user.getStatus() == null)
			user.setStatus(true);
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
	public String delete(int id) {
		if (logger.isDebugEnabled())
			logger.debug("Deleting user with id {}", id);
		try {
			String username = repository.findById(id).get().getUsername();
			repository.deleteById(id);
			logger.info("User with id={} was deleted", id);
			return username;
		} catch (Exception e) {
			logger.warn(e.toString());
			return null;
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("Load by username {}", username);
		Optional<User> user = repository.findByUsername(username);
		System.out.println(user.get());
		UserBuilder builder = null;
		if (user.isPresent()) {
			builder = org.springframework.security.core.userdetails.User.withUsername(username);
			builder.password(user.get().getPassword());
			builder.roles("USER");
			builder.disabled(!user.get().isEnabled());
			System.out.print(user.get().getStatus());
			return builder.build();
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
	}

	@Transactional
	public String block(Integer id) {
		if (logger.isDebugEnabled())
			logger.debug("Blocking user with id {}", id);
		User user = find(id).get();
		if (user != null) {
			String username = user.getUsername();
			user.setStatus(false);
			repository.save(user);
			logger.info("User with id={} was blocked", id);
			return username;
		}
		return null;
	}

	@Transactional
	public String unblock(Integer id) {
		if (logger.isDebugEnabled())
			logger.debug("Unblocking user with id {}", id);
		User user = find(id).get();
		if (user != null) {
			String username = user.getUsername();
			user.setStatus(true);
			repository.save(user);
			logger.info("User with id={} was unblocked", id);
			return username;
		}
		return null;
	}
}
