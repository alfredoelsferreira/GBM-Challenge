package com.gbm.fullstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gbm.fullstack.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);


}
