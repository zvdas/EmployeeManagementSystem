package com.employee.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.management.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	/* retrieve user details by email during login spring security */ 
	User findByEmail(String email);
}
