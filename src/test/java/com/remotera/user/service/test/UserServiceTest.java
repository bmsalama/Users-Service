package com.remotera.user.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.remotera.user.UserApplication;
import com.remotera.user.domain.User;
import com.remotera.user.dto.Gender;
import com.remotera.user.repository.UserRepository;
import com.remotera.user.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindAll() {
		User user = prepareUser();
		userService.createUser(user);
		List<User> actual = userService.getAllUsers();
		assertThat(actual).hasSize(userRepository.findAll().size());
	}

	@Test
	public void saveUserTest() {
		User user = prepareUser();
		User found = userService.createUser(user);
		assertThat(found.getId().length()).isGreaterThan(0);
	}

	@Test
	public void updateUserTest() {
		User user = prepareUser();
		User createdUser = userService.createUser(user);
		createdUser.setEmail("updated mail");
	
		Optional<User> found = userService.updateUser(createdUser);
		assertThat(found.get().getEmail()).isEqualTo("updated mail");
	}

	@Test
	public void deleteUserTest() {
		User user = prepareUser();
		User createdUser = userService.createUser(user);
		userService.deleteUser(createdUser.getId());
		assertThat(userRepository.findById(createdUser.getId())).isEqualTo(null);
	}


	@Test
	public void findByIdTest() {
		User user = prepareUser();
		User createdUser = userService.createUser(user);
		Optional<User> found = userService.findUserById(createdUser.getId());

		assertThat(found.get().getId()).isEqualTo(createdUser.getId());
	}

	private User prepareUser() {

		User user = new User();
		user.setFirstName("first name");
		user.setLastName("last name");
		user.setAge(20);
		user.setGender(Gender.MALE);
		return user;

	}

}
