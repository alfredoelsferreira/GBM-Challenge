package com.gbm.fullstack.configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.gbm.fullstack.model.Privilege;
import com.gbm.fullstack.model.Role;
import com.gbm.fullstack.model.User;
import com.gbm.fullstack.repository.PrivilegeRepository;
import com.gbm.fullstack.repository.RoleRepository;
import com.gbm.fullstack.repository.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;
	 
    @Autowired
    private UserRepository userRepository;
  
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PrivilegeRepository privilegeRepository;
  
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Override
    @Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
            return;
		}
        Privilege readPrivilege = createPrivilegeIfNotFound(Constants.READ);
        Privilege writePrivilege = createPrivilegeIfNotFound(Constants.WRITE);
  
        List<Privilege> adminPrivileges = Arrays.asList( readPrivilege, writePrivilege);        
        
        Role adminRole = createRoleIfNotFound(Constants.ADMINISTRATOR, adminPrivileges);
        Role userRole = createRoleIfNotFound(Constants.USER, Arrays.asList(readPrivilege));

        
        createUserIfNotFound("admin@admin.com", "admin", "Admin", "Test", adminRole);
        createUserIfNotFound("user@admin.com", "user", "User", "Test", userRole);

 
        alreadySetup = true;
		
	}
    
    @Transactional
    public User createUserIfNotFound(String email, String password, String firstName, String lastName, Role userRole) {
        User userFromDB =  userRepository.findByEmail(email);
        if (userFromDB == null) {
            User user = new User();
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRoles(Arrays.asList(userRole));

            user.setFirstName(firstName);
            user.setLastName(lastName);

            userRepository.save(user);


            return user;
        }
            
        return userFromDB;

    }
    
    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {
  
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }
    
    @Transactional
    private Role createRoleIfNotFound(
      String name, Collection<Privilege> privileges) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}
