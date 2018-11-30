package com.remotera.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.remotera.user.dto.Gender;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name="users")
@EqualsAndHashCode(of = "id", callSuper = false)
public class User {

	@Id
	private String id;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column
	private String address;
	@Column
	private String notes;
	@Column
	private Integer age;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gender gender;
	@Column
	private String mobileNumber;
	@Column
	private String email;
	

}
