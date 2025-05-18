package com.hafssa.gestion_commandes.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.hafssa.gestion_commandes.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}