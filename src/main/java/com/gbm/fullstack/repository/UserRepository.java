package com.gbm.fullstack.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbm.fullstack.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
