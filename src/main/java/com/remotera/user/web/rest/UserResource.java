package com.remotera.user.web.rest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.base.Preconditions;
import com.remotera.user.domain.User;
import com.remotera.user.dto.UserDto;
import com.remotera.user.service.UserService;

@RestController
@RequestMapping("/v1")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private Mapper mapper;

	@PostMapping("/users")
	public UserDto createUser(HttpServletRequest request, @Valid @RequestBody UserDto user) {
		return toResponse(userService.createUser(toModel(user)));
	}

	@PutMapping("/users/{id}")
	public UserDto updateUser(HttpServletRequest request, @PathVariable("id") String id,
			@Valid @RequestBody UserDto user) {
		user.setId(id);
		return toResponse(userService.updateUser(toModel(user)));
	}

	@GetMapping("/users")
	public List<UserDto> getAllUsers() {
		return toResponse(userService.getAllUsers());
	}

	@GetMapping("/users/{id}")
	public UserDto getUser(@PathVariable("id") String id) {
		return toResponse(userService.findUserById(id));
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable("id") String id) {
		userService.deleteUser(id);
	}

	private User toModel(UserDto source) {
		User model = mapper.map(source, User.class);
		return model;
	}

	private UserDto toResponse(Optional<User> source) {
		return source.isPresent() ? toResponse(source.get()) : null;
	}

	private UserDto toResponse(User source) {
		Preconditions.checkNotNull(source);
		return mapper.map(source, UserDto.class);
	}

	private List<UserDto> toResponse(List<User> source) {
		return source == null ? null : source.stream().map(this::toResponse).collect(Collectors.toList());
	}

}
