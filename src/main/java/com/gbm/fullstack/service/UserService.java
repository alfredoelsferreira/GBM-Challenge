package com.gbm.fullstack.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gbm.fullstack.model.User;
import com.gbm.fullstack.repository.UserRepository;

@Service
public class UserService implements IUserService , ISubject{
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private List<IObserver> observers = new ArrayList<IObserver>();
	
	private User user;

	@Override
	public User getUserByEmailAndPassword(String email, String password) {
		log.debug("Authenticating {}", email);
        User existingUser = repository.findByEmail(email);

        if (existingUser != null) {
        	if (!passwordEncoder.matches(password, existingUser.getPassword())) {
        		user = existingUser;
        		notifyObservers();
        		user = null;
        		return null;
            }
      
        }
        
        return existingUser;
	}

	@Override
	public void addObserver(IObserver ob) {
		observers.add(ob);	
	}

	@Override
	public void removeObserver(IObserver ob) {
		observers.remove(ob);
		
	}

	@Override
	public void notifyObservers() {
		for (IObserver observer : observers) {
            observer.update(user);
		}
		
	}

}
