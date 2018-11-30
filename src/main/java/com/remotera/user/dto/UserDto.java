package com.remotera.user.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserDto {

	private String id;
	@NotNull(message= "First name is mandatory")
	private String firstName;
	@NotNull(message= "Last name is mandatory")
	private String lastName;
	@NotNull(message= "Address is mandatory")
	private String address;

	private String notes;
	@NotNull(message= "Age is mandatory")
	private Integer age;
	@NotNull(message= "Gender is mandatory")
	private Gender gender;

	private String mobileNumber;

	private String email;

}
