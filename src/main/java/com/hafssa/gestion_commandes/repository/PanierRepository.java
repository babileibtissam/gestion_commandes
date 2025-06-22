package com.hafssa.gestion_commandes.repository;

import com.hafssa.gestion_commandes.model.Panier;
import com.hafssa.gestion_commandes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Long> {
    Optional<Panier> findByProduit(Product produit);
}
