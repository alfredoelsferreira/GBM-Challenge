package com.gbm.fullstack.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gbm.fullstack.model.LoginAttempt;
import com.gbm.fullstack.model.User;
import com.gbm.fullstack.repository.LoginAttemptRepository;
import com.gbm.fullstack.repository.UserRepository;

@Service
public class LoginAttemptService implements ILoginAttemptService , IObserver{

	@Autowired
	private LoginAttemptRepository loginAttemptRepository;
	
	@Autowired
	private IMailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void addLoginAttempt(User user) {
		LoginAttempt loginAttempt = user.getLoginAttempt();
		if (loginAttempt == null) {
			loginAttempt = new LoginAttempt();
			
		}
		loginAttempt.setLastModified(new Timestamp(System.currentTimeMillis()));
		Integer attempts = loginAttempt.getAttempts()+1;
		loginAttempt.setAttempts(attempts);
		
		loginAttemptRepository.saveAndFlush(loginAttempt);
		
		
		
		if (attempts >= 3) {
			// notify admin 
			mailService.sendSimpleMessage(
					"alfredo.els.ferreira@gmail.com", 
					"3 failed login attepemted", 
					"user " + user.getEmail() + " has attempted to login 3 times with invalid password");
			
		}
		user.setLoginAttempt(loginAttempt);
		userRepository.saveAndFlush(user);
	}
	
	@Override
	public void deleteLoginAttempt(User user) {
		LoginAttempt loginAttempt = loginAttemptRepository.getOne(user.getUserId());
		if (loginAttempt == null) {
			loginAttemptRepository.delete(loginAttempt);
		}
		
	}

	

	@Override
	public void update(Object data) {
		User user = (User) data;
		
		if (user != null) {
			addLoginAttempt(user);
		}
		
	}
}
