package com.hafssa.gestion_commandes.repository;

import com.hafssa.gestion_commandes.model.Order;
import com.hafssa.gestion_commandes.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Optionally: List<Order> findByStatus(String status);
	List<Order> findByUser(User user);

}
