package com.hafssa.gestion_commandes.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.hafssa.gestion_commandes.model.User;
import com.hafssa.gestion_commandes.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
	public User findByEmail(String email);
	User save(User user);

}
