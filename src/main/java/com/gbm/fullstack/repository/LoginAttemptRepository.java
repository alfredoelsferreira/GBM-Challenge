package com.gbm.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbm.fullstack.model.LoginAttempt;
import com.gbm.fullstack.model.User;

public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {
	LoginAttempt findByUser(User user);
}
