package com.employee.management.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.employee.management.dto.UserRegistrationDto;
import com.employee.management.model.User;

public interface UserService extends UserDetailsService {
	User save(UserRegistrationDto registrationDto);
}
