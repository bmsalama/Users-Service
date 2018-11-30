package com.remotera.user.web.rest.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.remotera.user.UserApplication;
import com.remotera.user.domain.User;
import com.remotera.user.dto.Gender;
import com.remotera.user.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserResourceTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	Gson gson;


	@Test
	public void saveUserTest() throws Exception {
		User user = createUser(false);

		mvc.perform(post("/v1/users/").header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("id", is(user.getId())));
	}

	@Test
	public void saveUserTestWithMissingFirstName() throws Exception {
		User user = createUser(false);
		user.setFirstName(null);

		mvc.perform(post("/v1/users/").header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void saveUserTestWithMissingLastName() throws Exception {
		User user = createUser(false);
		user.setLastName(null);

		mvc.perform(post("/v1/users/").header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void saveUserTestWithMissingAdress() throws Exception {
		User user = createUser(false);
		user.setAddress(null);

		mvc.perform(post("/v1/users/").header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void saveUserTestWithMissingAge() throws Exception {
		User user = createUser(false);
		user.setAge(null);

		mvc.perform(post("/v1/users/").header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}
	
	@Test
	public void saveUserTestWithMissingGender() throws Exception {
		User user = createUser(false);
		user.setGender(null);

		mvc.perform(post("/v1/users/").header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

	}

	@Test
	public void updateUserTest() throws Exception {
		User user = createUser(true);
		user.setFirstName("new first name");
		mvc.perform(put("/v1/users/" + user.getId()).header("Origin", "*").content(gson.toJson(user))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("firstName", is("new first name")));

	}

	@Test
	public void deleteUserTest() throws Exception {
		User user = createUser(true);
		mvc.perform(delete("/v1/users/" + user.getId()).header("Origin", "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void testFindAll() throws Exception {
		createUser(true);
		mvc.perform(get("/v1/users/").header("Origin", "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.length()", is(1)));
	}

	@Test
	public void findByIdTest() throws Exception {

		User user = createUser(true);

		mvc.perform(get("/v1/users/" + user.getId()).header("Origin", "*").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("firstName", is("first name")));
	}

	private User createUser(boolean save) {

		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setFirstName("first name");
		user.setLastName("last name");
		user.setAge(20);
		user.setGender(Gender.MALE);
		user.setAddress("address");
		if (save)
			user = userRepository.save(user);
		return user;

	}
}
