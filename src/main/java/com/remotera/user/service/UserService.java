package com.remotera.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.remotera.user.domain.User;
import com.remotera.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Transactional
	public User createUser(User user) {
		Preconditions.checkNotNull(user);
		if (user.getId() == null) {
			user.setId(UUID.randomUUID().toString());
		}

		return save(user);
	}

	private User save(User user) {
		return repository.save(user);
	}

	@Transactional
	public Optional<User> updateUser(User user) {
		Preconditions.checkNotNull(user);
		Preconditions.checkNotNull(user.getId(), "Id must not be empty");

		return findUserById(user.getId()).flatMap(x -> Optional.of(save(user)));
	}

	public List<User> getAllUsers() {
		return repository.findAll();
	}

	@Transactional
	public Optional<User> deleteUser(String id) {
		Preconditions.checkNotNull(id);
		return findUserById(id).map(x -> {
			repository.delete(x);
			return x;
		});
	}

	public Optional<User> findUserById(String id) {
		Preconditions.checkNotNull(id);
		User user = repository.findById(id);
		return Optional.ofNullable(user);
	}

}
