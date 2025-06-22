package com.hafssa.gestion_commandes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hafssa.gestion_commandes.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
}
