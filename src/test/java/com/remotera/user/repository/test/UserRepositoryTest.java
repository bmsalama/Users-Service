package com.remotera.user.repository.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.remotera.user.domain.User;
import com.remotera.user.dto.Gender;
import com.remotera.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void saveUserTest() {
		// given
		User user = addUser();

		// when
		User found = userRepository.findById(user.getId());

		// then
		assertThat(found.getId()).isEqualTo(user.getId());
	}

	private User addUser() {
		User user = new User();
		user.setFirstName("first name");
		user.setLastName("last name");
		user.setAge(20);
		user.setGender(Gender.MALE);
		user.setId(UUID.randomUUID().toString());
		user = entityManager.persist(user);
		entityManager.flush();

		return user;

	}
	@Test
	public void getAllTest() {
		addUser();
		List<User> actual = userRepository.findAll();
		assertThat(actual).hasSize(1);
	}

	@Test
	public void updateUserTest() {
		// given
		User user = addUser();

		// update user

		user.setAge(40);
		entityManager.persist(user);
		entityManager.flush();
		// when
		User found = userRepository.findById(user.getId());

		// then
		assertThat(found.getAge()).isEqualTo(user.getAge());
	}

	@Test
	public void deleteUserTest() {
		// given
		User user = addUser();
		// delete user

		entityManager.remove(user);
		entityManager.flush();
		// when
		User found = userRepository.findById(user.getId());

		// then
		assertThat(found).isEqualTo(null);
	}

	@Test
	public void findByIdTest() {
		// given
		User user = addUser();

		// when
		User found = userRepository.findById(user.getId());

		// then
		assertThat(found.getId()).isEqualTo(user.getId());
	}
}
