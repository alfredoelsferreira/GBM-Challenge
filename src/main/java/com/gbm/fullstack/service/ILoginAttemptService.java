package com.gbm.fullstack.service;

import com.gbm.fullstack.model.LoginAttempt;
import com.gbm.fullstack.model.User;

public interface ILoginAttemptService {
	void addLoginAttempt(User user);
	void deleteLoginAttempt(User user);

}
