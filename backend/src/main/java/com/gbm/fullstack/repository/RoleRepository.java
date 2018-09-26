package com.gbm.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbm.fullstack.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
