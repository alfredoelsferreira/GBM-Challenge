package com.gbm.fullstack.service;

import com.gbm.fullstack.model.User;

public interface IUserService {
	User getUserByEmailAndPassword(String email, String password);
}
