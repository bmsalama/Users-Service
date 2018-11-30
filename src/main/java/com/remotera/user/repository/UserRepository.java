package com.remotera.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remotera.user.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findById(String id);

   
}
